package days

import util.Vector
import util.safeRangeTo
import util.toVector
import util.vto
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day09 : Day(9) {
    val points = inputList.map { it.toVector() }
    override fun part1() : Any {
        return points.maxOf { a ->
            points.maxOf { b ->
                (abs(a.x.toLong()- b.x.toLong()) + 1) * ((abs(a.y.toLong()- b.y.toLong())) + 1)
            }
        }
    }

    override fun part2() : Any {
        val p = points + points.first()

        var max = 0L
        return points.maxOf { a ->
            println("tape - $a")
            points.maxOf { b ->
                val area = (abs(a.x.toLong()- b.x.toLong()) + 1) * ((abs(a.y.toLong()- b.y.toLong())) + 1)
                if (area < max) {
                    max = area
                    return@maxOf 0
                }
                val bottomLeft = a.x vto b.y
                val topRight = b.x vto a.y


                if (isInPath(a, p) && isInPath(b, p) && isInPath(bottomLeft, p) && isInPath(topRight, p)) {
                    val horizontal= (min(a.x, b.x)..max(a.x,b.x)).flatMap { listOf(it vto a.y, it vto b.y)  }
                    val vertical= (min(a.y,b.y) ..max(a.y,b.y)).flatMap { listOf(a.x vto it, b.x vto it) }
                    val poly = (horizontal + vertical).toSet()

                    if (poly.all {
                        isInPath(it, points + points.first())
                    }) {
                        (abs(a.x.toLong()- b.x.toLong()) + 1) * ((abs(a.y.toLong()- b.y.toLong())) + 1)
                    } else {
                        0
                    }
                    } else 0
            }
        }
    }

    val cache = mutableMapOf<Vector, Boolean>()

    fun isInPath(point: Vector, path: List<Vector>): Boolean {
        if (point in cache) return cache[point]!!
        var i = false
        path.windowed(2).forEach{ (a, b) ->
            if (point.x == a.x && point.y == a.y) {
//                println("  $point in corner! Yes!")
                cache[point] = true
                return true
            }
            if (a.y == b.y && point.x in a.x.safeRangeTo(b.x) && point.y == a.y ) {
//                println("  $point in boundary! Yes!")
                cache[point] = true
                return true
            }
            if (a.x == b.x && point.y in a.y.safeRangeTo(b.y) && point.x == a.x) {
//                println("  $point in boundary! Yes!")
                cache[point] = true
                return true
            }
            if (a.y > point.y != b.y > point.y) {
                val slope = (point.x - a.x) * (b.y - a.y) - (b.x - a.x) * (point.y - a.y)
                if (slope == 0) {
//                    println("  $point in boundary! Yes!")
                    return true
                }
                if (slope < 0 != b.y < a.y) {
//                    println("  $point crossing between $a and $b! Inverting!")
                    i = !i
                }
            }
        }
        cache[point] = i
//        println("  ${i}")
        return i
    }
}