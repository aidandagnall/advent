package days

import kotlin.math.abs

class Day02 : Day(2) {

    private val reports = inputList.map { it.split(" ").map(String::toInt) }

    private fun List<List<Int>>.isSameDirection(): Boolean =
        all { (a, b) -> a < b } || all { (a, b) -> a > b }

    private fun List<Int>.isSafe(): Boolean =
        windowed(2).let { pairs ->
            pairs.isSameDirection() && pairs.all { (a, b) -> abs(a - b) in 1..3}
        }

    override fun part1() : Any = reports.count { it.isSafe() }

    override fun part2() : Any = reports.count { report ->
            report.isSafe () || List(report.size) { i ->
                report.take(i) + report.drop(i + 1)
            }.any { it.isSafe() }
        }
}