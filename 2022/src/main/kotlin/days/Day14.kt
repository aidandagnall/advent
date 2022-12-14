package days

import java.lang.Integer.min
import kotlin.math.max

class Day14 : Day(14) {
    val rocks = mutableSetOf<Pair<Int,Int>>()
    val sand = mutableSetOf<Pair<Int,Int>>()

    init {
        inputList.forEach { line ->
            line.split(" -> ").map { it.split(",") }.map { (a, b) -> a.toInt() to b.toInt() }
                .windowed(2).map { (start, end) ->
                    (min(start.first, end.first)..max(start.first, end.first)).forEach { x ->
                        (min(start.second, end.second)..max(start.second, end.second)).forEach { y ->
                            rocks.add(x to y)
                        }
                    }
                }
        }
    }

    val max = rocks.maxOf { it.second } + 2

    private fun addSand(includeFloor: Boolean) : Boolean {
        var newSand = 500 to 0
        var canMove = true

        while (canMove) {

            if (!includeFloor && rocks.all { newSand.second > it.second }) {
                return false
            }

            if(newSand.first to (newSand.second + 1) !in rocks && newSand.first to (newSand.second + 1) !in sand) {
                newSand = newSand.first to newSand.second + 1
            } else if ((newSand.first - 1) to (newSand.second + 1) !in rocks && (newSand.first - 1) to (newSand.second + 1) !in sand) {
                newSand = (newSand.first - 1) to newSand.second + 1
            } else if ((newSand.first + 1) to (newSand.second + 1) !in rocks && (newSand.first + 1) to (newSand.second + 1) !in sand) {
                newSand = (newSand.first + 1) to newSand.second + 1
            } else {
                canMove = false
            }
        }

        sand.add(newSand)
        return true
    }

    override fun part1() : Any {
        while (addSand(false)) {}
        return sand.count()
    }

    override fun part2() : Any {
        (0..1000).map { rocks.add(it to max) }
        while (500 to 0 !in sand) {
            addSand(true)
        }

        val min = sand.minOf { it.first }
        val maxX = sand.maxOf { it.first }

        val output = (0..max).joinToString("\n") { y ->

            (min..maxX).joinToString("") {  x ->
                if (x to y in sand) {
                    "o"
                } else if (x to y in rocks) {
                    "#"
                } else " "
            }
        }

        println(output)

        return sand.count()
    }
}