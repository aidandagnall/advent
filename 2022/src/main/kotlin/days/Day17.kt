package days

import util.plus
import kotlin.math.max

class Day17 : Day(17) {

    val jets = inputList.first().trim().toList().map { if (it == '<') -1 else 1 }

    // shapes defined as offsets from the current height of the cave
    val shapes = listOf(
        listOf(2 to 3, 3 to 3, 4 to 3, 5 to 3),           // - shape
        listOf(3 to 3, 2 to 4, 3 to 4, 4 to 4, 3 to 5),   // + shape
        listOf(2 to 3, 3 to 3, 4 to 3, 4 to 4, 4 to 5),   // L shape
        listOf(2 to 3, 2 to 4, 2 to 5, 2 to 6),           // I shape
        listOf(2 to 3, 3 to 3, 2 to 4, 3 to 4),           // square shape
    )

    override fun part1() : Any {
        val state = mutableSetOf<Pair<Int,Int>>()
        var height = 0
        var index = 0
        (0 until 2022).forEach { rock ->
            var points = shapes[rock % 5].map { it + (0 to height) }
            while(true) {
                points = pushRocks(state, points, jets[index++ % jets.size])

                if (points.map { it + (0 to -1) }.any { it.second < 0 || it in state}) break
                points = points.map { it + (0 to -1) }
            }
            state.addAll(points)
            height = max(height, points.maxOf { it.second } + 1)
        }
        return height
    }

    override fun part2() : Any {
        var index = 0
        val state = mutableSetOf<Pair<Int,Int>>()
        val shapeIndexes = mutableListOf<Int>()
        val heights = mutableListOf(0)
        (0 until 10000000).forEach { rock ->
            if (rock > 300) {
                if (shapeIndexes.size > 0 && shapeIndexes.size % 2 == 0) {
                    val size = shapeIndexes.size / 2
                    if (shapeIndexes.take(size) == shapeIndexes.drop(size)) {
                        val initialSize = rock - 2 * (size + 1)
                        val cycleCount = (1_000_000_000_000 - initialSize) / size
                        val cycleHeight = heights[size + initialSize] - heights[initialSize]
                        val remainder = (1_000_000_000_000 - (size * cycleCount) - initialSize)
                        val remainderHeight =
                            heights[(initialSize + size + remainder).toInt()] - heights[initialSize + size]
                        return heights[initialSize] + (cycleHeight * cycleCount) + remainderHeight
                    }
                }
                shapeIndexes += index % jets.size
            }

            var points = shapes[rock % 5].map { it + (0 to heights.last()) }
            while(true) {
                points = pushRocks(state, points, jets[index++ % jets.size])

                if (points.map { it + (0 to -1) }.any { it.second < 0 || it in state}) break
                points = points.map { it + (0 to -1) }
            }

            state.addAll(points)
            heights.add(max(heights.last(), points.maxOf { it.second } + 1))
        }
        return 0
    }

    private fun pushRocks(state: Set<Pair<Int,Int>>, points: List<Pair<Int,Int>>, direction: Int): List<Pair<Int,Int>> {
        return points.map { it + (direction to 0) }.let { new ->
            if (new.any { it.first < 0 || it.first > 6 } || new.any { it in state} ) points else new
        }
    }
}