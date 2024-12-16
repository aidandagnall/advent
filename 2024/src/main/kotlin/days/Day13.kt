package days

import util.filterNotEmpty

class Day13 : Day(13) {
    private val regex = """(\d+)""".toRegex()
    private val groups = inputList.filterNotEmpty().chunked(3).map { it.joinToString("") }
        .map { machine->
            regex.findAll(machine).map { it.groupValues[1].toLong() }.toList()
            .let { Triple(it[0] to it[1], it[2] to it[3], it[4] to it[5]) }
        }

    override fun part1() : Long = groups.mapNotNull { (a, b, p) -> getPresses(a, b, p) }.sum()
    override fun part2() : Long = groups.mapNotNull { (a, b, p) ->
        getPresses(a, b, p.first + 10000000000000L to p.second + 10000000000000L)
    }.sum()

    private fun getPresses(a: Pair<Long,Long>, b: Pair<Long,Long>, prize: Pair<Long,Long>): Long? {
        val (x1, y1) = a
        val (x2, y2) = b
        val (p, q) = prize

        val aPresses = ((p * y2 - q * x2).toDouble() / (x1 * y2 - y1 * x2).toDouble())
        val bPresses = ((q * x1 - p * y1).toDouble() / (x1 * y2 - y1 * x2).toDouble())

        if ((aPresses + bPresses) % 1.0 != 0.0) return null

        return (aPresses.toLong() * 3) + bPresses.toLong()
    }
}