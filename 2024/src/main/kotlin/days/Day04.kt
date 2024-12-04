package days

import util.plus

class Day04 : Day(4) {
    private val points = inputList.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c -> (x to y) to c }
    }.toMap()
    //                              N        NE       E       SE      S       SW       W        NW
    private val directions = listOf(0 to -1, 1 to -1, 1 to 0, 1 to 1, 0 to 1, -1 to 1, -1 to 0, -1 to -1 )

    override fun part1() : Int =
        points.filterValues { it == 'X' }.map { (point, _) ->
                directions.count { point.getString(it, 4) == "XMAS" }
            }.sum()

    override fun part2() : Int =
        points.filterValues { it == 'M' || it == 'S' }
            .count { (point, _) -> point.getX().all { it == "MAS" || it == "SAM" } }

    private fun Pair<Int,Int>.getString(direction: Pair<Int,Int>, length: Int): String =
        List(length) {
            this + (direction.first * it to direction.second * it)
        }.mapNotNull { points[it] }.joinToString("")

    private fun Pair<Int,Int>.getX() = listOf(
        getString(1 to 1, 3),
        (this + (2 to 0)).getString(-1 to 1, 3)
    )
}