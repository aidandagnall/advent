package days

import util.lcm

class Day20 : Day(20) {
    enum class State { HIGH, LOW }
    sealed interface Module {
        val name: String
        val destinations: List<String>

        fun process(inputs: List<Message>): State?;
    }
    data class FlipFlop(
        override val name: String,
        override val destinations: List<String>
    ): Module {
        private var on: Boolean = false
        override fun process(inputs: List<Message>): State? {
            return if (inputs.any { it.value == State.LOW } && inputs.isNotEmpty()) {
                on = !on
                if (on) State.HIGH else State.LOW
            } else {
                null
            }
        }
    }

    data class Conjunction(
        override val name: String,
        override val destinations: List<String>,
    ): Module {
        val memory = mutableMapOf<String,State>()

        override fun process(inputs: List<Message>): State? {
            inputs.forEach { (src, _, value) ->
                memory[src] = value
            }

            return if (memory.values.all { it == State.HIGH }) State.LOW else State.HIGH
        }
    }

    data class Broadcast(
        override val name: String,
        override val destinations: List<String>
    ): Module {
        override fun process(inputs: List<Message>): State? {
            return if (inputs.first().value == State.LOW) State.LOW else State.HIGH
        }
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
                    module.memory[srcName] = State.LOW
                }
            }
        }
    }

    data class Message(val source: String, val destination: String, val value: State)

    private fun runPart1(): Long {
        return (1..1000).fold(0L to 0L) { acc, i ->
            val queue = mutableListOf<Message>(
                Message("button", "broadcaster", State.LOW)
            )

            val messages = mutableMapOf<String,MutableList<Message>>(
                "broadcaster" to mutableListOf(Message("button", "broadcaster", State.LOW))
            )

            val processed = mutableListOf<Message>()
            var count = 0

            while(queue.isNotEmpty()) {
                val next = queue.removeFirst()
                count++
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

            processed.fold(acc) { (highs, lows), msg ->
                when(msg.value) {
                    State.HIGH -> (highs + 1) to lows
                    State.LOW -> highs to lows + 1
                }
            }
        }.let { (highs, lows) -> highs * lows }
    }

    override fun part1() : Any {
//        return runPart1()
        return 0
    }

    override fun part2() : Any {
        val finals = modules.filterValues { "vd" in it.destinations }.keys.toMutableSet()
        val counts = mutableListOf<Long>()
        var presses = 1L

        while (finals.isNotEmpty()) {
            val queue = mutableListOf<Message>(
                Message("button", "broadcaster", State.LOW)
            )

            val messages = mutableMapOf<String, MutableList<Message>>(
                "broadcaster" to mutableListOf(Message("button", "broadcaster", State.LOW))
            )

            val processed = mutableListOf<Message>()

            while (queue.isNotEmpty()) {
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

                if (next.destination == "vd" && next.value == State.HIGH) {
                    finals.remove(next.source)
                    counts.add(presses)
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
            presses++
        }
        return counts.fold(1L) { acc, it -> acc.lcm(it)}
    }
}