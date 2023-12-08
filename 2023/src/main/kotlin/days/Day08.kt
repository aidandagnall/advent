package days

import util.lcm

class Day08 : Day(8) {
    private val directions = inputList.first()
    private val map = inputList.drop(2).associate {
        """([\dA-Z]{3}) = \(([\dA-Z]{3}), ([\dA-Z]{3})\)""".toRegex()
            .matchEntire(it)!!.groupValues.let { (_, a, b, c) ->
                a to (b to c)
            }
    }

    private fun solve(start: String, endPattern: String): Int = generateSequence(start to 0) { (current, index) ->
        (map[current]?.let {  (left, right) ->
            if (directions[index % directions.length] == 'L') left else right
        } ?: endPattern) to index + 1
    }.first { (node, _) -> node.endsWith(endPattern) }.second

    override fun part1() : Any = solve("AAA", "ZZZ")
    override fun part2() : Any = map.keys.filter { it.last() == 'A' }
        .map { solve(it, "Z") }
        .fold(1L) { acc, i -> acc.lcm(i.toLong()) }
}