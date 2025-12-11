package days

import util.Vector
import util.getAllPairs
import util.safeRangeTo
import util.toVector
import util.vto
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day09 : Day(9) {
    val points = inputList.map { it.toVector() }

    fun Pair<Vector, Vector>.area() = (abs(first.x.toLong() - second.x.toLong()) + 1) * ((abs(first.y.toLong() - second.y.toLong())) + 1)

    override fun part1() : Any = points.getAllPairs().maxOf { it.area() }

    override fun part2() : Any {
        val p = points + points.first()
        var max = 0L

        return points.getAllPairs().maxOf { (a,b) ->
            val area = (abs(a.x.toLong()- b.x.toLong()) + 1) * ((abs(a.y.toLong()- b.y.toLong())) + 1)
            if (area < max) {
                return@maxOf 0
            }

            val x1 = min(a.x, b.x) + 1
            val x2 = max(a.x, b.x) - 1
            val y1 = min(a.y, b.y) + 1
            val y2 = max(a.y, b.y) - 1

            val edges = listOf(
                (x1 vto y1) to (x2 vto y1),
                (x2 vto y1) to (x2 vto y2),
                (x2 vto y2) to (x1 vto y2),
                (x1 vto y2) to (x1 vto y1),
            )

            val valid = p.windowed(2)
                .all { (p1, p2) ->
                    edges.all { (s, e) ->

                        val pIsHorizontal = p1.y == p2.y
                        val seIsHorizontal = s.y == e.y

                        if (pIsHorizontal == seIsHorizontal) return@all true
                        if (pIsHorizontal) {
                            return@all !(s.x in p1.x.safeRangeTo(p2.x) && p1.y in s.y.safeRangeTo(e.y))
                        }
                        return@all !(s.y in p1.y.safeRangeTo(p2.y) && p1.x in s.x.safeRangeTo(e.x))
                    }
                }

            return@maxOf if (valid) {
                if (area > max) {
                    max = area
                }
                area
            } else 0
        }
    }
}