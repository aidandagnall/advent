package days

import util.plus

class Day10 : Day(10) {
    private val regex = Regex("""position=< *(-?\d+), *(-?\d+)> velocity=< *(-?\d+), *(-?\d+)>""")
    private val stars: Map<Int, Pair<Int,Int>>
    private val velocities: Map<Int, Pair<Int,Int>>

    init {
        val combined = inputList.mapIndexed { i, it ->
            regex.matchEntire(it)!!.destructured.let { (a, b, c, d) ->
                i to ((a.toInt() to b.toInt()) to (c.toInt() to d.toInt()))
            }
        }
        stars = combined.associate { (key, entry) -> key to entry.first }
        velocities = combined.associate { (key, entry) -> key to entry.second}
    }

    private fun Iterable<Pair<Int,Int>>.buildGrid(): String {
        return "\n" + (minOf { it.second }..maxOf { it.second }).joinToString("\n") { y ->
            (minOf { it.first }..maxOf { it.first }).joinToString("") { x ->
                if (x to y in this) "█" else " "
            }
        }
    }

    private tailrec fun simulate(input: Map<Int, Pair<Int,Int>>, time: Int = 0):  Pair<Map<Int, Pair<Int,Int>>, Int> {
        // I've assumed that the result would be 8 characters, as is typical for AoC, so would be roughly 70 chars wide
        return if (input.values.maxOf{ it.first } - input.values.minOf { it.first } <= 70 ) {
            input to time
        }
        else simulate(input.map { (key, pos) ->
            key to (pos + velocities[key]!!)
        }.toMap(), time + 1)
    }

    override fun part1() : Any = simulate(stars).first.values.buildGrid()
    override fun part2() : Any = simulate(stars).second
}