package days

class Day05 : Day(5) {

    private val instructionRegex = Regex("""move (\d+) from (\d+) to (\d+)""")

    private val initialStack = inputList.indexOfFirst{ it == "" }.let {
        val initial = inputList.take(it - 1)
        val numStacks = inputList[it - 1].takeLast(3).trim().toInt()

        val stacks = List(numStacks) { mutableListOf<Char>()}

        initial.forEach {
            (0 until numStacks * 4 step 4).mapIndexed inner@{ stackIndex, charIndex ->
                if (charIndex + 3 > it.length) return@forEach
                val str = it.substring(charIndex, charIndex + 3).trim()
                if (str.isEmpty()) return@inner
                stacks[stackIndex].add(str.drop(1).first())
            }
        }

        stacks.map { stack -> stack.toList() }
    }

    private val instructions = inputList.drop(inputList.indexOf("") + 1)
        .map {
            instructionRegex.find(it)!!.let { matches ->
                matches.groupValues.let {(str, a, b, c) ->
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