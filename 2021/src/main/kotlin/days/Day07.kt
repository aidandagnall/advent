package days

import kotlin.math.abs
import kotlin.math.roundToInt

class Day07 : Day(7) {
    override fun part1() : Any {
        val input = inputList.first()
            .split(",")
            .map { it.toInt() }

        return (0..(input.maxOf { it }) )
            .minOf { target -> input.sumOf { abs(target - it) } }
    }

    override fun part2() : Any {
        val input = inputList.first()
            .split(",")
            .map { it.toInt() }

        return (0..(input.maxOf { it }) )
            .minOf { target ->
                input.sumOf { abs(target - it) * (abs(target - it) + 1) / 2 }
            }
    }
}