package days

import kotlin.math.sign
import kotlin.math.abs

class Day09 : Day(9) {

    // map to series of one step instructions
    // e.g. "U 4" -> ['U', 'U', 'U', 'U']
    private val input = inputList.map { it.split(" ") }.map { (a, b) -> List(b.toInt()) { a.first() } }.flatten()

    override fun part1(): Any = runSimulation(2)

    override fun part2(): Any = runSimulation(10)

    private fun runSimulation(knotCount: Int): Int {
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        val knots = MutableList(knotCount) { 0 to 0 }

        input.forEach {
            knots.forEachIndexed { knotIndex, knot ->
                knots[knotIndex] = knot.moveKnot(knots.getOrNull(knotIndex - 1), it)
                if (knotIndex == knots.size - 1) visitedPositions.add(knots[knotIndex])
            }
        }

        return visitedPositions.count()
    }

    private fun Pair<Int, Int>.moveKnot(previous: Pair<Int, Int>?, direction: Char): Pair<Int, Int> {
        if (previous == null) {
            return when (direction) {
                'R' -> this.first to this.second + 1
                'L' -> this.first to this.second - 1
                'U' -> this.first + 1 to this.second
                'D' -> this.first - 1 to this.second
                else -> this
            }
        }

        // if previous knot is up to 1 grid square away, do not need to move
        if (abs(previous.first - this.first) <= 1 && abs(previous.second - this.second) <= 1) return this

        val xDiff = sign(previous.first - this.first.toDouble()).toInt()
        val yDiff = sign(previous.second - this.second.toDouble()).toInt()
        return this.first + xDiff to this.second + yDiff
    }
}