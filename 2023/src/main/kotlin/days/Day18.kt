package days

import util.plus
import kotlin.math.abs

class Day18 : Day(18) {
    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        fun toVector(): Pair<Long,Long> = when(this) {
            UP -> 0L to -1L
            DOWN -> 0L to 1L
            LEFT -> -1L to 0L
            RIGHT -> 1L to 0L
        }
    }
    data class Point(val direction: Direction, val count: Long, val rgb: String)
    val points = inputList.map {
        """([RDLU]) (\d+) \(#([\da-f]{6})\)""".toRegex().matchEntire(it)!!
            .groupValues
            .let { (_, dir, count, rgb) ->
                when(dir) {
                    "U" -> Point(Direction.UP, count.toLong(), rgb)
                    "D" -> Point(Direction.DOWN, count.toLong(), rgb)
                    "L" -> Point(Direction.LEFT, count.toLong(), rgb)
                    else -> Point(Direction.RIGHT, count.toLong(), rgb)
                }
            }
    }

    val map: List<Pair<Long,Long>> = points.runningFold(0L to 0L) { acc, point ->
        acc + point.direction.toVector().let { (x, y) -> x * point.count to y * point.count }
    }
    val bigMap = points.runningFold(0L to 0L) { acc, point ->
        val dir = when(point.rgb.takeLast(1)) {
            "0" -> Direction.RIGHT
            "1" -> Direction.DOWN
            "2" -> Direction.LEFT
            else -> Direction.UP
        }
        acc + dir.toVector().let { (x, y) -> x * point.rgb.take(5).toInt(16) to y * point.rgb.take(5).toInt(16) }
    }

    private fun area(coordinates: List<Pair<Long,Long>>): Long =
        (coordinates.foldIndexed(0L) { i, acc, (x, y) ->
            val next = if (i == coordinates.size - 1) coordinates[0] else coordinates[i + 1]
            val last = if (i == 0) coordinates.last() else coordinates[i - 1]
            val count: Long = abs(x - next.first) + abs(y - next.second)

            acc + (x * (next.second - last.second)) + count
        } * 0.5).toLong() + 1

    override fun part1() : Any = area(map)

    override fun part2() : Any = area(bigMap)
}