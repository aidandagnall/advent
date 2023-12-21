package days

import util.manhattan

class Day21 : Day(21) {

    val map = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            (x to y) to c
        }
    }.toMap()

    val start = map.filterValues { it == 'S' }.keys.first()

    val minX = map.keys.minOf { it.first }
    val maxX = map.keys.maxOf { it.first }
    val minY = map.keys.minOf { it.second }
    val maxY = map.keys.maxOf { it.second }

    val steps = mutableSetOf<Pair<Int,Int>>()
    override fun part1() : Any {
        return step(start, 64)
    }

    private fun step(start: Pair<Int,Int>, count: Int): Int {
        val steps = setOf<Pair<Int,Int>>(start)
        return (1..64).fold(steps) { acc, i ->
            acc.flatMap { it.neighbours().filter { it in map && map[it] != '#' } }.toSet()
        }.count()
    }

    private fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> {
        return listOf(
            this.first + 1 to this.second,
            this.first - 1 to this.second,
            this.first to this.second + 1,
            this.first to this.second - 1,
        )
    }

    override fun part2() : Any {
        return 0
    }
}