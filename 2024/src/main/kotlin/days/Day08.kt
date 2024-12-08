package days

import util.getAllPairs
import util.getPoints
import util.manhattan
import util.slopeTo

typealias Point = Pair<Int,Int>

class Day08 : Day(8) {
    private val antennas = inputList.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            if (c != '.') {
                (x to y) to c
            } else null
        }.filterNotNull()
    }.groupBy { it.second }.values.map { it.map { it.first } }

    private fun sameLine(p1: Point, p2: Point, p3: Point): Boolean {
        if (p1 == p2 && p1 != p3) return true
        if (p1 == p3 && p1 != p2) return true

        return p1.slopeTo(p2) == p1.slopeTo(p3)
    }

    private fun countMatches(predicate: (Point, Point, Point) -> Boolean): Int =
        inputList.getPoints().count { p ->
            antennas.any { group ->
                group.getAllPairs().any { (a, b) -> predicate(p, a, b) }
            }
        }

    override fun part1() : Any =
        countMatches { p, a1, a2 -> sameLine(p, a1, a2) && 2 * p.manhattan(a1) == p.manhattan(a2)}

    override fun part2() : Any =
        countMatches { p, a1, a2 -> sameLine(p, a1, a2) }
}