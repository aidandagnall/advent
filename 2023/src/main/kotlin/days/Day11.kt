package days

import util.safeRangeTo
import util.transpose
import kotlin.math.abs

class Day11 : Day(11) {
    private val galaxies = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == '#') x to y  else null
        }.filterNotNull()
    }

    private val emptyRows = inputList.map(String::toList)
        .findEmptyRowIndices { line -> line.all { it == '.' } }

    private val emptyColumns = inputList.map(String::toList)
        .transpose()
        .findEmptyRowIndices { line -> line.all { it == '.' } }

    override fun part1() : Any = solveImage(2)
    override fun part2() : Any = solveImage(999_999)

    fun solveImage(expansion: Long): Long = galaxies.sumOf { a ->
        galaxies.filter { it != a }.sumOf { b ->
            a.galaxyManhattan(b, expansion)
        }
    } / 2

    // regular manhattan distance + the number of expanded rows and columns passed through (* the size of expansion)
    private fun Pair<Int,Int>.galaxyManhattan(other: Pair<Int,Int>, size: Long = 1L): Long =
        abs(this.first - other.first) + abs(this.second - other.second) +
            this.first.safeRangeTo(other.first).count { it in emptyColumns } * (size - 1) +
            this.second.safeRangeTo(other.second).count { it in emptyRows } * (size - 1)

    private fun <T> List<List<T>>.findEmptyRowIndices(isEmpty: (List<T>) -> Boolean): Set<Int> =
        mapIndexed { index, row -> if (isEmpty(row)) index else null }
            .filterNotNull()
            .toSet()
}