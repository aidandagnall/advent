package days

import kotlin.math.sign

class Day01 : Day(1) {
    fun run(): List<Int> = inputList.runningFold(50) { acc, command ->
            val (direction, clicks) = """([RL])(\d+)""".toRegex().matchEntire(command)!!.destructured
            if (direction == "R") acc + clicks.toInt() else acc - clicks.toInt()
        }

    override fun part1(): Any = run().count { it.mod(100) == 0 }
    override fun part2(): Any {
        return run().windowed(2).sumOf { (a, b) ->
            IntProgression.fromClosedRange(a + (b - a).sign, b, (b - a).sign).count { it.mod(100) == 0 }
        }
    }
}