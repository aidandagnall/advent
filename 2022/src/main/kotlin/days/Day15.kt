package days

import util.manhattan

class Day15 : Day(15) {
    private val regex = """x=(-?\d+), y=(-?\d+)""".toRegex()
    val input = inputList.map { it.removePrefix("Sensor at ").replace(" closest beacon is at ", "") }
        .map { it.split(":") }
        .map { (a, b) ->
            regex.matchEntire(a)!!
                .groupValues.let { it[1].toInt() to it[2].toInt() } to regex.matchEntire(b)!!
                .groupValues.let { it[1].toInt() to it[2].toInt() }
        }

    override fun part1() : Any {
        val clearPositions = mutableSetOf<Int>()
        val yLevel = 2_000_000

        input.forEach { (beacon, scanner) ->
            val dist = beacon.manhattan(scanner)
            val distToY = beacon.manhattan(beacon.first to yLevel)

            if (distToY < dist) {
                val xRange = dist - distToY
                (0..xRange).forEach {
                    clearPositions.add(beacon.first + it)
                    clearPositions.add(beacon.first - it)
                }
            }
        }
        input.forEach { (_, beacon) ->
            if (beacon.second == yLevel) {
                clearPositions.remove(beacon.first)
            }
        }
        return clearPositions.count()
    }

    override fun part2() : Any {
        val range = 0..4_000_000
        val dists = input.map { (beacon, scanner) -> beacon to beacon.manhattan(scanner) }
        return dists.flatMap { (beacon, dist) ->
                (1..dist).flatMap { x ->
                    setOf(
                        beacon.first + x + 1 to beacon.second + (dist - x),
                        beacon.first - x - 1 to beacon.second - (dist - x),
                    ).filter { it.first in range && it.second in range }
                }
        }.first {
            dists.all { (beacon, dist) ->
                beacon.manhattan(it) > dist
            }
        }.let { it.first * 4_000_000L + it.second }
    }
}