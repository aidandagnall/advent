package days

import util.neighbours4
import util.parseGrid

class Day10 : Day(10) {

    private val points = inputList.parseGrid { point, height -> point to height.digitToInt() }.toMap()
    private val routes = points.filterValues {it == 0 }.map { (pos, _) -> bfs(pos) }

    override fun part1(): Any = routes.sumOf { route -> route.map { it.last() }.distinct().count() }
    override fun part2(): Any = routes.sumOf { it.count() }

    private fun bfs(start: Pair<Int, Int>): Set<List<Pair<Int,Int>>> {
        val queue = mutableListOf(listOf(start))
        val paths = mutableSetOf<List<Pair<Int,Int>>>()

        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            if (points[v.last()] == 9) {
                paths.add(v)
                continue
            }

            v.last().neighbours4()
                .filter { it in points }.filter { points[it] == points[v.last()]!! + 1 }
                .forEach { queue.add(v + it) }
        }
        return paths
    }
}