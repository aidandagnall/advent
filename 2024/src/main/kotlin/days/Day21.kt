package days

import util.parseGrid

class Day21 : Day(21) {
    private val codes = inputList.take(5)

    private val doorPad = listOf("789", "456", "123", " 0A").parseGrid { pos, c ->
        if (c == ' ') null else c to pos
    }.toMap()

    private val robotPad = listOf(" ^A", "<v>").parseGrid { pos, c ->
        if (c == ' ') null else c to pos
    }.toMap()


    override fun part1(): Long =
        codes.map { it to findInput(doorPad, it, 3) }
            .sumOf { it.complexity() }

    override fun part2(): Long =
        codes.map { it to findInput(doorPad, it, 26) }
            .sumOf { (code, input) -> code.removeSuffix("A").toLong() * input }

    private fun Pair<String, Long>.complexity() = first.removeSuffix("A").toLong() * second

    private val cache = mutableMapOf<Triple<Int, String, Int>, Long>()

    private fun findInput(pad: Map<Char, Point>, input: String, robots: Int): Long {
        if (robots == 0) return input.length.toLong()
        if (Triple(pad.size, input, robots) in cache) return cache[Triple(pad.size, input, robots)]!!

        return ("A$input").toList().windowed(2).sumOf { (a, b) ->
            getPaths(pad, pad[a]!!, pad[b]!!).minOf { findInput(robotPad, it, robots - 1) }
        }.also { cache[Triple(pad.size, input, robots)] = it }
    }

    private fun getPaths(pad: Map<Char, Point>, start: Point, end: Point): List<String> {
        val dx = end.first - start.first
        val dy = end.second - start.second

        val horizontal = if (dx > 0) ">".repeat(dx) else "<".repeat(-dx)
        val vertical = if (dy > 0) "v".repeat(dy) else "^".repeat(-dy)

        val out = mutableListOf<String>()
        if (start.first to end.second in pad.values) out.add(vertical + horizontal + "A")
        if (end.first to start.second in pad.values) out.add(horizontal + vertical + "A")
        return out
    }
}
