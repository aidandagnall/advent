package days

import util.filterNotEmpty

class Day19 : Day(19) {
    private val possible = inputList.first().split(", ")
    private val rest = inputList.drop(2).filterNotEmpty()
    private val cache = mutableMapOf<String, Long>()

    override fun part1() : Any = rest.count { find(it) > 0 }
    override fun part2() : Any = rest.sumOf { find(it) }

    private fun find(t: String): Long = when(t) {
        "" -> 1
        in cache -> cache[t]!!
        else -> possible
                .filter { t.startsWith(it) }
                .sortedByDescending { it.length }.sumOf {
                    find(t.removePrefix(it))
                }.also { cache[t] = it }
    }
}