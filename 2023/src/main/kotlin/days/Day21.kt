package days

import util.neighbours4

class Day21 : Day(21) {

    private val map = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            (x to y) to c
        }
    }.toMap()

    private val start = map.filterValues { it == 'S' }.keys.first()

    private val maxX = map.keys.maxOf { it.first }
    private val maxY = map.keys.maxOf { it.second }

    override fun part1() : Any {
        return step(start, 64)
    }

    private fun step(start: Pair<Int,Int>, count: Int): Int {
        val steps = setOf<Pair<Int,Int>>(start)
        return (1..count).fold(steps) { acc, i ->
            acc.flatMap { it.neighbours4().filter { (x, y) ->
                val adj = Math.floorMod(x, maxX + 1) to Math.floorMod(y, maxY + 1)
                adj in map && map[adj] != '#' }
            }.toSet()
        }.count()
    }

    // a popular method from r/adventofcode
    // this is far too smart for me to find by myself
    override fun part2() : Any {
        val (a0, a1, a2) = (0..2).map { step(start, 65 + 131 * it) }

        val n = 26501365L / (maxX + 1).toLong()
        val b0 = a0.toLong()
        val b1 = (a1 - a0).toLong()
        val b2 = (a2 - a1).toLong()

        return b0 + (b1 * n) + (((n * (n - 1L)) / 2L) * (b2 - b1))
    }
}