package days

import util.parseGrid

class Day12 : Day(12) {
    val presents = inputGroupedList.dropLast(1).map {
        it.drop(1).parseGrid { pair, ch -> if (ch == '#') pair else null }
    }

    val tests = inputGroupedList.last().map {
        val (size, rest) = it.split(": ")
        val (x, y) = size.split("x").map { it.toInt() }
        val needed = rest.split(" ").map { it.toInt() }
        Pair(x to y, needed)
    }

    override fun part1() : Any =tests.count { (size, needed) ->
        size.first * size.second  > needed.mapIndexed { index, i -> presents[index].count() * i }.sum()
    }
    override fun part2() : Any = "Merry Christmas"
}