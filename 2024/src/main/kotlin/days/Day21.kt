package days

import util.minus
import util.neighbours4
import util.parseGrid

class Day21 : Day(21) {
    private val codes = inputList.take(5)

    private val doorPad = listOf("789", "456", "123", " 0A").parseGrid { pos, c ->
        if (c == ' ') null
        else c to pos
    }.toMap()

    private val robotPad = listOf(" ^A", "<v>").parseGrid { pos, c ->
        if (c == ' ') null
        else c to pos
    }.toMap()

    private fun search(pad: Map<Char, Point>, start: Point, end: Point): List<String> {
        val queue = mutableListOf(start to "")
        val out = mutableListOf<String>()

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (out.isNotEmpty()) {
                if (current.second.length > out.first().length) {
                    break;
                }
            }

            if (current.first == end) {
                out.add(current.second + "A")
                continue
            }

            current.first.neighbours4()
                .filter { it in pad.values }
                .forEach {
                    val char = when (it - current.first) {
                        1 to 0 -> ">"
                        -1 to 0 -> "<"
                        0 to 1 -> "v"
                        0 to -1 -> "^"
                        else -> error("")
                    }
                    queue.add(it to current.second + char)
                }
            }
        return out
    }

    val cache = mutableMapOf<Triple<Int, String, Int>, Long>()

    private fun findInput(pad: Map<Char, Point>, input: String, robots: Int): Long = when {
        robots == 0 -> input.length.toLong()

        Triple(pad.size, input, robots) in cache -> cache[Triple(pad.size, input, robots)]!!

        else -> ("A$input").toList().windowed(2).sumOf { (a, b) ->
            search(pad, pad[a]!!, pad[b]!!).minOf { findInput(robotPad, it, robots - 1) }
        }.also { cache[Triple(pad.size, input, robots)] = it }
    }

    override fun part1(): Long =
        codes.map { it to findInput(doorPad, it, 3) }
        .sumOf { it.complexity() }

    override fun part2(): Long =
        codes.map { it to findInput(doorPad, it, 26) }
        .sumOf { (code, input) -> code.removeSuffix("A").toLong() * input }


    private fun Pair<String, Long>.complexity() = first.removeSuffix("A").toLong() * second
}
