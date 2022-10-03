package days

import java.lang.Integer.max
import java.lang.Integer.min

class Day25 : Day(25) {

    override fun part1() : Any {
        val sizeX = inputList.first().length
        val sizeY = inputList.size
        var grid = mutableMapOf<Pair<Int,Int>, Int>().apply {
            inputList.forEachIndexed { j, line ->
                line.forEachIndexed { i, char ->
                    when (char) {
                        '>' -> this[i to j] = EAST
                        'v' -> this[i to j] = SOUTH
                    }
                }
            }
        }

        var changed = true
        var steps = 0
        while (changed) {
            changed = false
            val newGrid = grid.toMutableMap()
            grid.filter { it.value == EAST }.forEach {
                it.key.let { (i, j) ->
                    val newX = if (i < sizeX - 1) i + 1 else 0
                    if (newX to j !in grid.keys) {
                        changed = true
                        newGrid.remove(i to j)
                        newGrid[newX to j] = EAST
                    }
                }
            }
            grid = newGrid.toMutableMap()
            grid.filter { it.value == SOUTH }.forEach {
                it.key.let { (i, j) ->
                    val newY = if (j < sizeY - 1) j + 1 else 0
                    if (i to newY !in grid.keys) {
                        changed = true
                        newGrid.remove(i to j)
                        newGrid[i to newY] = SOUTH
                    }
                }
            }
            grid = newGrid
            steps++
        }


        return steps
    }

    override fun part2() : Any {
        return "Merry (belated) Christmas!"
    }

    companion object {
        const val EAST = 1
        const val SOUTH = 2
    }
}