package days

import util.plus
import kotlin.math.min
import kotlin.math.max

class Day14 : Day(14) {
    private val rocks = inputList.flatMap { line ->
        line.split(" -> ").map { it.split(",") }.map { (a, b) -> a.toInt() to b.toInt() }
            .windowed(2).flatMap { (start, end) ->
                (min(start.first, end.first)..max(start.first, end.first)).flatMap { x ->
                    (min(start.second, end.second)..max(start.second, end.second)).map { y ->
                        x to y
                    }
                }
            }
    }.toMutableSet()
    private val maxY : Int = rocks.maxOf { it.second }
    private val sand = mutableSetOf<Pair<Int,Int>>()

    private fun addSand(includeFloor: Boolean) : Boolean {
        var new = 500 to 0

        while (true) {
            if (!includeFloor && new.second > maxY) return false

            new += when {
                new + (0 to 1)  !in rocks && new + (0 to 1)  !in sand -> (0 to 1)
                new + (-1 to 1) !in rocks && new + (-1 to 1) !in sand -> (-1 to 1)
                new + (1 to 1)  !in rocks && new + (1 to 1)  !in sand -> (1 to 1)
                else -> {
                    sand.add(new)
                    return true
                }
            }
        }
    }

    override fun part1() : Any {
        while (addSand(false)) {}
        return sand.count()
    }

    override fun part2() : Any {
        (0..1000).forEach { rocks.add(it to maxY + 2) }
        while (500 to 0 !in sand) addSand(true)
        return sand.count()
    }
}