package days

import util.manhattan

class Day15 : Day(15) {
    private val regex = """x=(-?\d+), y=(-?\d+)""".toRegex()
    private val input = inputList.map { it.removePrefix("Sensor at ").replace(" closest beacon is at ", "") }
        .map { it.split(":") }
        .map { (a, b) ->
            Pair(regex.matchEntire(a)!!.groupValues.let { it[1].toInt() to it[2].toInt() },
                    regex.matchEntire(b)!!.groupValues.let { it[1].toInt() to it[2].toInt() })
        }

    override fun part1() : Any = getClearPositionsInRow()
    override fun part2() : Any = getMissingSensorFreq()

    fun getClearPositionsInRow(y: Int = 2_000_000): Int {
        val clearPositions = mutableSetOf<Int>()

        input.forEach { (beacon, scanner) ->
            val dist = beacon.manhattan(scanner)
            val distToY = beacon.manhattan(beacon.first to y)

            if (distToY < dist) {
                val xRange = dist - distToY
                (0..xRange).forEach {
                    clearPositions.add(beacon.first + it)
                    clearPositions.add(beacon.first - it)
                }
            }
        }
        input.forEach { (_, beacon) ->
            if (beacon.second == y) clearPositions.remove(beacon.first)
        }
        return clearPositions.count()
    }

    fun getMissingSensorFreq(size: Int = 4_000_000): Long {
        val distances = input.map { (beacon, scanner) -> beacon to beacon.manhattan(scanner) }
        return distances.flatMap { (beacon, dist) ->
            (1..dist).flatMap { x ->
                setOf(
                    beacon.first + x + 1 to beacon.second + (dist - x),
                    beacon.first - x - 1 to beacon.second - (dist - x),
                ).filter { it.first in 0..size && it.second in 0..size }
            }
        }.first {
            distances.all { (beacon, dist) ->
                beacon.manhattan(it) > dist
            }
        }.let { it.first * 4_000_000L + it.second }
    }
}