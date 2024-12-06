package days

import util.plus

class Day06 : Day(6) {
    private val walls = inputList.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            if (c == '#') x to y else null
        }.filterNotNull()
    }.toSet()

    private val xRange = 0..< inputList.first().length
    private val yRange = 0..< inputList.count()

    private val initialGuard = inputList.flatMapIndexed { y: Int, s: String ->
        s.mapIndexed { x, c ->
            if (c == '^') x to y else null
        }.filterNotNull()
    }.first().let { Guard(it, 0 to -1) }

    data class Guard(val position: Pair<Int,Int>, val direction: Pair<Int,Int>) {
        fun move(walls: Set<Pair<Int,Int>>): Guard {
            if (position + direction in walls) {
                return copy(direction = direction.turn())
            }
            return copy(position = position + direction)
        }

      private fun Pair<Int,Int>.turn(): Pair<Int,Int> = when(this) {
            0 to -1 -> 1 to 0
            1 to 0 -> 0 to 1
            0 to 1 -> -1 to 0
            -1 to 0 -> 0 to -1
            else -> error("")
        }
    }

    private fun getVisits(walls: Set<Pair<Int,Int>>): List<Pair<Int,Int>> = run(walls).first.map { it.position }

    private fun run(walls: Set<Pair<Int,Int>>): Pair<Set<Guard>, Boolean> {
        val history = mutableSetOf<Guard>()
        var current = initialGuard
        while (true) {
            if (current.position.isOutOfRange()) {
                return history to false
            }
            current = current.move(walls)
            if (current in history) {
                return history to true
            }
            history.add(current)
        }
    }

    private fun Pair<Int,Int>.isOutOfRange(): Boolean = first !in xRange || second !in yRange

    override fun part1() : Any = getVisits(walls).toSet().count()

    override fun part2() : Any =
        getVisits(walls)
            .filter { it != initialGuard.position && !it.isOutOfRange() }
            .toSet()
            .map { run(walls + it) }
            .count { it.second }
}