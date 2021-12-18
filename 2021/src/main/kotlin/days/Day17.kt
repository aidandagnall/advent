package days

import kotlin.math.sign

class Day17 : Day(17) {
    data class Area(val x: Pair<Int,Int>, val y: Pair<Int,Int>) {
        val xRange = x.first..x.second
        val yRange = y.first..y.second
    }
    private val input = inputList.first().removePrefix("target area: ")
        .split(", ")
        .map { it.split("=")[1] }
        .map { it.split("..").let { coord -> coord[0].toInt() to coord[1].toInt() } }
    private val target = Area(input[0], input[1])

    override fun part1() : Any = fire().maxOf { it.value }

    override fun part2() : Any = fire().size

    private fun fire(): Map<Pair<Int,Int>,Int> {
        val velocities = mutableMapOf<Pair<Int,Int>,Int>()
        (1..target.x.second).forEach { xVel ->
            (-target.y.first downTo target.y.first).forEach { yVel ->
                val (valid, result) = passesRange(xVel, yVel)
                if (valid) velocities[(xVel to yVel)] = result
            }
        }
        return velocities.toMap()
    }

    private fun passesRange(initialXVel: Int, initialYVal: Int): Pair<Boolean, Int> {
        var (x, y) = 0 to 0
        var (xVel, yVel) = initialXVel to initialYVal
        while(x <= target.x.second && y >= target.y.first && !(xVel == 0 && x !in target.xRange)) {
            x += xVel
            y += yVel
            if (x in target.xRange && y in target.yRange) break
            xVel -= sign(xVel.toDouble()).toInt()
            yVel -= 1
        }
        return (x in target.xRange && y in target.yRange) to initialYVal * (initialYVal + 1) / 2
    }
}