package days

class Day05 : Day(5) {
    private val initialStack = inputList.take(inputList.indexOf("")).let { input ->
        List(input.last().split(" ").count { it != " " && it.isNotEmpty() }) { stack ->
            input.dropLast(1).map { it.getOrElse(stack * 4 + 1) { ' ' } }.filter {it != ' '}
        }
    }

    private val instructions = inputList.drop(inputList.indexOf("") + 1).map { line ->
            Regex("""move (\d+) from (\d+) to (\d+)""").find(line)!!.groupValues
                .drop(1).map(String::toInt)
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