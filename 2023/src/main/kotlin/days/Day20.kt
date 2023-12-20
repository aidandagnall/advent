package days

import util.lcm

class Day20 : Day(20) {
    enum class Pulse { HIGH, LOW }

    sealed interface Module {
        val name: String
        val destinations: List<String>
        fun process(input: Message): Pulse?
        fun copy(): Module
    }

    data class FlipFlop(
        override val name: String,
        override val destinations: List<String>,
        val initialState: Boolean = false,
    ): Module {
        private var on: Boolean = initialState
        override fun process(input: Message): Pulse? =
            when (input.value) {
                Pulse.LOW -> {
                    on = !on
                    if (on) Pulse.HIGH else Pulse.LOW
                }
                else -> null
            }
        override fun copy(): FlipFlop = FlipFlop(name, destinations.toList(), on)
    }

    data class Conjunction(
        override val name: String,
        override val destinations: List<String>,
        val initialMemory: Map<String,Pulse> = emptyMap(),
    ): Module {
        val memory = initialMemory.toMutableMap()
        override fun process(input: Message): Pulse {
            memory[input.source] = input.value
            return if (memory.values.all { it == Pulse.HIGH }) Pulse.LOW else Pulse.HIGH
        }
        override fun copy(): Conjunction = Conjunction(name, destinations.toList(), memory)
    }

    data class Broadcast(override val name: String, override val destinations: List<String>): Module {
        override fun process(input: Message): Pulse = if (input.value == Pulse.LOW) Pulse.LOW else Pulse.HIGH
        override fun copy(): Broadcast = Broadcast(name, destinations.toList())
    }

    private val modules = inputList.associate {
        val (name, destinations) = it.split(" -> ").let { (n, d) -> n to d.trim().split(", ") }
        val module = when(name.first()) {
            '%' -> FlipFlop(name.drop(1), destinations)
            '&' -> Conjunction(name.drop(1), destinations)
            'b' -> Broadcast(name, destinations)
            else -> error("Unsupported module")
        }
        module.name to module
    }

    init {
        modules.values.filterIsInstance<Conjunction>().forEach { module ->
            modules.filterValues { module.name in it.destinations }.forEach { (srcName, _) ->
                module.memory[srcName] = Pulse.LOW
            }
        }
    }

    data class Message(val source: String, val destination: String, val value: Pulse)
    data class State(val index: Long, val modules: Map<String,Module>, val messages: List<Pair<Long,Message>>)

    private fun pressButton(input: State): State {
        val modules = input.modules.mapValues { (_, v) -> v.copy() }.toMap()
        val queue = mutableListOf(Message("button", "broadcaster", Pulse.LOW))
        val processed = mutableListOf<Message>()

        while(queue.isNotEmpty()) {
            val next = queue.removeFirst()
            val module = modules[next.destination] ?: continue

            processed.add(next)
            val output = module.process(next) ?: continue

            module.destinations.forEach {
                val message = Message(module.name, it, output)
                if (it in modules) queue.add(message)
                else processed.add(message)
            }
        }

        return State(input.index + 1, modules, input.messages + processed.map { input.index + 1 to it })
    }

    private val initial = State(0, modules, emptyList())

    override fun part1() : Any = (1..1000).fold(initial) { acc, _ -> pressButton(acc)}
            .messages
            .map { it.second }
            .partition { it.value == Pulse.HIGH }
            .let { (high, low) -> high.count() * low.count() }

    override fun part2() : Any {
        val rxParent = modules.values.first { "rx" in it.destinations }.name
        val rxGrandparents = modules.values.filter { rxParent in it.destinations }.map { it.name }

        return generateSequence(initial) { state ->
            pressButton(state).let { (a, b, c) ->
                State(a, b, c.filter { it.second.destination == rxParent && it.second.value == Pulse.HIGH })
            }
        }.map(State::messages)
        .first { messages -> rxGrandparents.all { gp -> gp in messages.map { it.second.source }} }
        .fold(1L) { acc, (index, _) -> acc.lcm(index)}
    }
}