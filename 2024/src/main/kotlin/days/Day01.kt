package days

import util.filterNotEmpty
import kotlin.math.abs

class Day01 : Day(1) {
    private val lines = inputList.map {
        it.split(" ").filterNotEmpty()
    }.map { (a, b) -> a.toInt() to b.toInt() }

    private val first = lines.map { it.first }.sorted()
    private val second = lines.map { it.second }.sorted()

    override fun part1(): Any =
        first.zip(second).sumOf {
            abs(it.first - it.second)
        }

    override fun part2(): Any =
        first.sumOf { left ->
            left * second.count { right -> left == right }
        }
}