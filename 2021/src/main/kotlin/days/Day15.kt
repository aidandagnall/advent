package days

import java.util.*
import kotlin.math.abs

class Day15 : Day(15) {
    private val input = inputList.map { line -> line.split("").filter { i -> i != "" }.map { it.toInt() } }
    override fun part1(): Any {
        return explore(0 to 0, input.size - 1 to input.size - 1, input)
    }

    override fun part2(): Any {
        val newInput = (0..4).flatMap { x ->
            input.map { line -> (0..4).flatMap { y -> line.map { it + y + x }.map { if (it > 9) it - 9 else it } } }
        }
        return explore(0 to 0, (input.size * 5) - 1 to (input.size * 5) - 1, newInput)
    }

    private fun explore(start: Pair<Int, Int>, goal: Pair<Int, Int>, input: List<List<Int>>): Int {
        val distance = mutableMapOf<Pair<Int, Int>, Int>()
        val t = PriorityQueue<Pair<Int, Int>>(1) { (x1, y1), (x2, y2) ->
            (input[x1][y1] + distance.getOrDefault(x1 to y1, 9999))
                .compareTo(input[x2][y2] + distance.getOrDefault(x2 to y2, 9999))
        }
        distance[start] = 0
        t.add(start)
        while (t.isNotEmpty()) {
            val u = t.remove()
            if (u == goal) break
            u.getAdjacent()
                .filter { (x, y) -> x in input.indices && y in input[0].indices }
                .forEach { (x, y) ->
                    val alt = distance.getOrDefault(u, 9999) + input[x][y]
                    if (alt < distance.getOrDefault(x to y, 9999)) {
                        distance[x to y] = alt
                        t.add(x to y)
                    }
                }
        }
        return distance[goal]!!
    }

    private fun Pair<Int, Int>.getAdjacent(): List<Pair<Int, Int>> {
        val (x, y) = this
        return (x - 1..x + 1).flatMap { i -> (y - 1..y + 1).map { j -> i to j } }
            .filter { (i, j) -> i to j != x to y }
            .filter { (i, j) -> abs(i - x) + abs(j - y) == 1 }
    }
}