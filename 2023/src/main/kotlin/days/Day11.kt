package days

import util.manhattan
import util.transpose
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 : Day(11) {
    private val galaxies = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == '#') x to y  else null
        }.filterNotNull()
    }

    private val emptyRows = inputList.mapIndexed { index, line -> if (line.all { it == '.' }) index else null}.filterNotNull().toSet()
    private val emptyColumns = transpose(inputList.map { it.toList() }).mapIndexed { index, line -> if (line.all { it == '.' }) index else null}.filterNotNull().toSet()

    override fun part1() : Any {
        return galaxies.sumOf { a ->
            galaxies.sumOf { b ->
                if (a == b) 0
                else a.galaxyManhatten(b)
            }
        } / 2
        return 0
    }

    private fun Pair<Int,Int>.galaxyManhatten(other: Pair<Int,Int>, size: Long = 1L): Long {
        return abs(this.first - other.first) + abs(this.second - other.second) +
          (min(this.first,other.first)..max(this.first,other.first)).count{ it in emptyColumns } * size +
                (min(this.second, other.second)..max(this.second,other.second)).count { it in emptyRows } * size

    }

    override fun part2() : Any {
        return galaxies.sumOf { a ->
            galaxies.sumOf { b ->
                if (a == b) 0
                else a.galaxyManhatten(b, 999_999)
            }
        } / 2
    }
}