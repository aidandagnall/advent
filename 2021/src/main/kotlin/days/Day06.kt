package days

import java.math.BigInteger

class Day06 : Day(6) {
    override fun part1() : Any {
        return runSimulation(80)
    }

    override fun part2() : Any {
        return runSimulation(256)
    }

    private fun runSimulation(days : Int) : Long {
        var input = inputList
            .first()
            .split(',')
            .map {it.toInt()}
        var values = (0..8).map { input.count { i -> i == it }.toLong() }.toList()

        (1..days).forEach { _ ->
            val newFish = values[0]
            values = values.mapIndexed { i, it ->
                when(i) {
                    8 -> newFish
                    6 -> (values[7] + newFish)
                    else -> values[i + 1]
                }
            }
        }
        return values.sum()
    }
}