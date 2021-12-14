package days

import kotlin.math.ceil

class Day14 : Day(14) {
    private val startingPairs = inputList.first()
        .windowed(2)
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }
    private val rules = inputList.drop(2).associate {
        it.split(" -> ").let { (pair, insertion) -> pair to insertion }
    }

    override fun part1(): Any = getLargestDiff(10)

    override fun part2(): Any = getLargestDiff(40)

    private fun getLargestDiff(steps: Int): Long {
        val freq = (1..steps).fold(startingPairs) { acc, _ -> acc.applyRules() }
            .map { (pair, freq) -> listOf(pair[0] to freq, pair[1] to freq) }
            .flatten()
            .groupBy { it.first }
            .map { (_, values) -> ceil(values.sumOf { it.second }.toDouble() / 2).toLong() }
        return freq.maxOf { it } - freq.minOf { it }
    }

    private fun Map<String, Long>.applyRules(): Map<String, Long> {
        val m = this.toMutableMap()
        (keys zip values).forEach { (a, b) ->
            m[a] = m[a]!! - b
            m["${a[0]}${rules[a]}"] = m.getOrDefault("${a[0]}${rules[a]}", 0) + b
            m["${rules[a]}${a[1]}"] = m.getOrDefault("${rules[a]}${a[1]}", 0) + b
        }
        return m.toMap()
    }
}