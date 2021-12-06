package days

import java.lang.Integer.max
import kotlin.math.sign
import java.lang.Math.abs as absKot

class Day05 : Day(5) {
    private val regex = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")
    override fun part1() : Any {
        val grid = mutableMapOf<Pair<Int,Int>, Int>()
        inputList.map {
            regex.matchEntire(it)!!
            .destructured
            .let { matches ->
                val (x1, y1, x2, y2) = matches.toList().map { match -> match.toInt() }
                if (x1 == x2 || y1 == y2) {
                    pointsBetween(Pair(x1, y1), Pair(x2, y2)).forEach { (x, y) ->
                        if (Pair(x, y) !in grid)
                            grid[Pair(x, y)] = 0
                        grid[Pair(x, y)] = grid[Pair(x, y)]!!.plus(1)
                    }
                }
            }
        }
        return grid.filter { it.value >= 2 }.count()
    }

    override fun part2() : Any {
        val grid = mutableMapOf<Pair<Int,Int>, Int>()
        inputList.map {
            regex.matchEntire(it)!!
            .destructured
            .let { matches ->
                val (x1, y1, x2, y2) = matches.toList().map { it.toInt() }
                pointsBetween(Pair(x1, y1), Pair(x2, y2)).forEach { (x, y) ->
                    if (Pair(x, y) !in grid)
                        grid[Pair(x, y)] = 0
                    grid[Pair(x,y)] = grid[Pair(x,y)]!!.plus(1)
                }
            }
        }
        return grid.filter { it.value >= 2 }.count()
    }

    private fun pointsBetween(a : Pair<Int, Int>, b : Pair<Int, Int>) : List<Pair<Int, Int>> {
        val xDiff = sign((b.first - a.first).toDouble()).toInt()
        val yDiff = sign((b.second - a.second).toDouble()).toInt()
        val num = max(absKot(a.first - b.first), absKot(a.second - b.second))
        return (0..num).map {
            Pair(a.first + it * xDiff , a.second + it *  yDiff)
        }
    }
}