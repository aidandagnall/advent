package days

class Day05 : Day(5) {

    private val instructionRegex = Regex("""move (\d+) from (\d+) to (\d+)""")
    private val initialStack = inputList.take(inputList.indexOf("")).let { input ->
        val state = input.dropLast(1)
        val numStacks = input.last()
            .split(" ")
            .count { it != " " && it.isNotEmpty() }

        List(numStacks) { stack ->
            state.map { it.getOrElse(stack * 4 + 1) { ' ' } }.filter {it != ' '}
        }
    }

    private val instructions = inputList.drop(inputList.indexOf("") + 1).map {
            instructionRegex.find(it)!!.let { matches ->
                matches.groupValues.let {(_, a, b, c) ->
                    Triple(a.toInt(), b.toInt(), c.toInt())
                }
            }
        }

    override fun part1() : Any {
        return instructions.fold(initialStack.toMutableList()) { acc, (num, src , dest) ->
            acc.apply {
                this[dest - 1] = this[src - 1].take(num).reversed() + this[dest - 1]
                this[src - 1] = this[src - 1].drop(num)
            }
        }.joinToString("") { it.first().toString() }
    }

    override fun part2() : Any {
        return instructions.fold(initialStack.toMutableList()) { acc, (num, src , dest) ->
            acc.apply {
                this[dest - 1] = this[src - 1].take(num) + this[dest - 1]
                this[src - 1] = this[src - 1].drop(num)
            }
        }.joinToString("") { it.first().toString() }
    }
}