package days

import util.neighbours8
import util.plus
import util.positiveMod
import util.product
import util.takeWhileInclusive

class Day14 : Day(14) {
    data class Robot(val id: Int, val position: Pair<Int,Int>, val velocity: Pair<Int,Int>)

    private val xRange = 101
    private val yRange = 103
    private val regex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()
    private val initialRobots = inputList.mapIndexed { i, it ->
        regex.matchEntire(it)!!.groupValues.let { (_, x, y, a, b) ->
            Robot(i, x.toInt() to y.toInt(), a.toInt() to b.toInt())
        }
    }

    private fun wait(): Sequence<List<Robot>> = generateSequence(initialRobots) { robots ->
        robots.map { it.walk() }
    }

    private fun Robot.walk(): Robot = copy(position = (position + velocity).let { (x, y) ->
        x.positiveMod(xRange) to y.positiveMod(yRange)
    })

    private fun Pair<Int,Int>.score() = when {
        first in 0..<(xRange / 2) && second in 0..<(yRange / 2) -> 1
        first in (xRange / 2) + 1..xRange && second in 0..<(yRange / 2) -> 2
        first in 0..<(xRange / 2) && second in (yRange / 2) + 1..yRange -> 3
        first in (xRange / 2) + 1..xRange && second in (yRange / 2) + 1..yRange -> 4
        else -> null
    }

    override fun part1() : Any =
        wait().take(101).last()
            .mapNotNull { (_, pos, _) -> pos.score() }
            .groupingBy { it }.eachCount()
            .map { it.value }
            .product()

    override fun part2() : Any =
        wait().takeWhileInclusive { robots ->
            val positions = robots.map { it.position }.toSet()
            val proximityScore = positions.sumOf { it.neighbours8().count { it in positions } } / positions.count().toFloat()
            proximityScore < 1
        }.also { println(it.last().visualise()) }.count() - 1

    private fun List<Robot>.visualise(): String {
        val positions = map { it.position }.toSet()
        return (0..yRange).joinToString("\n") { y ->
            (0..xRange).joinToString("") { x ->
                if (x to y in positions) positions.count { it == x to y }.toString()
                else "."
            }
        }
    }
}