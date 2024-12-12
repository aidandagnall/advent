package days

import util.*

class Day12 : Day(12) {
    private val points = inputList.parseGrid { pos, c -> pos to c }.toMap()
    private val groups: List<Set<Point>> = buildList {
        points.forEach { (point, plant) ->
            if (this.any { point in it }) return@forEach

            val g = mutableListOf(point)
            val queue = mutableListOf(point)
            while(queue.isNotEmpty()) {
                queue.removeFirst().neighbours4().filter { it !in g }.filter { points[it] == plant }.forEach {
                    g.add(it)
                    queue.add(it)
                }
            }

            add(g.toSet())
        }
    }

    override fun part1(): Int = groups.sumOf { it.perimeter() * it.area() }
    override fun part2(): Int = groups.sumOf { it.numSides() * it.area() }

    private fun Set<Point>.area(): Int = count()
    private fun Set<Point>.perimeter(): Int = sumOf { a -> a.neighbours4().count { it !in this } }
    private fun Set<Point>.numSides() = scale(3).edges().corners().count()

    private fun Set<Point>.isCorner(point: Point): Boolean =
        listOf(Direction.NORTH to Direction.SOUTH, Direction.EAST to Direction.WEST).none { (a, b) ->
            (point + a) in this && (point + b) in this
        }

    private fun Set<Point>.edges(): Set<Point> = flatMap { it.neighbours8().filter { it !in this } }.toSet()
    private fun Set<Point>.corners() = filter { isCorner(it) }.toSet()
}