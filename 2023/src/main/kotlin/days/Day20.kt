package days

import util.lcm
import util.plus

class Day20 : Day(20) {
    enum class Pulse { HIGH, LOW }
    sealed interface Module {
        val name: String
        val destinations: List<String>
        fun process(inputs: List<Message>): Pulse?;
        fun copy(): Module;
    }
    data class FlipFlop(
        override val name: String,
        override val destinations: List<String>,
        val initialState: Boolean = false,
    ): Module {
        private var on: Boolean = initialState
        override fun process(inputs: List<Message>): Pulse? {
            return if (inputs.any { it.value == Pulse.LOW } && inputs.isNotEmpty()) {
                on = !on
                if (on) Pulse.HIGH else Pulse.LOW
            } else {
                null
            }
        }
        override fun copy(): FlipFlop = FlipFlop(name, destinations.toList(), on)
    }

    data class Conjunction(
        override val name: String,
        override val destinations: List<String>,
        val initialMemory: Map<String,Pulse> = emptyMap(),
    ): Module {
        val memory = initialMemory.toMutableMap()
        override fun process(inputs: List<Message>): Pulse? {
            inputs.forEach { (src, _, value) ->
                memory[src] = value
            }
            return if (memory.values.all { it == Pulse.HIGH }) Pulse.LOW else Pulse.HIGH
        }
        override fun copy(): Conjunction = Conjunction(name, destinations.toList(), memory)
    }

    data class Broadcast(
        override val name: String,
        override val destinations: List<String>
    ): Module {
        override fun process(inputs: List<Message>): Pulse? {
            return if (inputs.first().value == Pulse.LOW) Pulse.LOW else Pulse.HIGH
        }
        override fun copy(): Broadcast = Broadcast(name, destinations.toList())
    }

    val modules = inputList.associate {
        val name = it.removePrefix("%").removePrefix("&").takeWhile { it != ' ' }
        val destinations = it.split("->").last().trim().split(", ")
        val module = when(it.first()) {
            '%' -> FlipFlop(name, destinations)
            '&' -> Conjunction(name, destinations)
            'b' -> Broadcast(name, destinations)
            else -> error("Unsupported module")
        }
        name to module
    }

    init {
        modules.forEach { (name, module) ->
            if (module is Conjunction) {
                val sources = modules.filterValues { name in it.destinations }
                sources.forEach { (srcName, _) ->
                    module.memory[srcName] = Pulse.LOW
                }
            }
        }
    }

    data class Message(val source: String, val destination: String, val value: Pulse)

    private fun pressButton(input: Map<String,Module>): Pair<Map<String,Module>,List<Message>> {
        val modules = input.mapValues { (_, v) -> v.copy() }.toMap()
        val queue = mutableListOf(
            Message("button", "broadcaster", Pulse.LOW)
        )

        val messages = mutableMapOf(
            "broadcaster" to mutableListOf(Message("button", "broadcaster", Pulse.LOW))
        )

        val processed = mutableListOf<Message>()

        while(queue.isNotEmpty()) {
            val next = queue.removeFirst()
            val module = modules[next.destination] ?: continue

            val output = if (module is Conjunction) {
                val t = module.process(messages[module.name]!!.take(1))
                val m = messages[module.name]!!.removeFirst()
                processed.add(m)

                t
            } else {
                queue.removeAll { it.destination == next.destination }
                val t = module.process(messages[module.name] ?: emptyList())

                messages[module.name]?.let {
                    processed.addAll(it)
                    it.clear()
                }

                t
            }

            if (output != null) {
                module.destinations.forEach {
                    val message = Message(module.name, it, output)
                    if (it in modules) {
                        queue.add(message)
                    }

                    if (it in modules) {
                        if (it in messages) {
                            messages[it]!!.add(message)
                        } else {
                            messages[it] = mutableListOf(message)
                        }
                    } else {
                        processed.add(message)
                    }
                }
            }
        }

        return modules to processed
    }

    override fun part1() : Any {
        val (_, result) = (1..1000).fold(modules to (0L to 0L)) { (current, sum), _ ->
            val (next, messages) = pressButton(current)
            next to (sum + (messages.count { it.value == Pulse.HIGH }.toLong() to messages.count { it.value == Pulse.LOW }.toLong()))
        }

        return result.first * result.second
    }

    override fun part2() : Any {
        val rxParent = modules.values.first { "rx" in it.destinations }.name
        val rxGrandparents: List<String> = modules.values.filter { rxParent in it.destinations }.map { it.name }

        data class State(val index: Long, val modules: Map<String,Module>, val messages: List<Pair<Long,Message>>)

        val initial = State(1, modules, emptyList())
        val (_, _, cycles) = generateSequence(initial) { (index, state, messages) ->
            val (next, msgs) = pressButton(state)
            val targetMessages = msgs.filter { it.destination == rxParent && it.value == Pulse.HIGH }.map { index to it }

            State(index + 1, next, messages + targetMessages)
        }.first { (_, _, msgs) -> rxGrandparents.all { it in msgs.map { it.second.source } } }

        return rxGrandparents.map { gp -> cycles.first { it.second.source == gp }.first }
            .reduce { acc, l -> acc.lcm(l)}
    }
}