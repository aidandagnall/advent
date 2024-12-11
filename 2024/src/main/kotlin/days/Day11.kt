package days

import util.filterNotEmpty

class Day11 : Day(11) {
    private val initialRocks = inputList.first().split(" ").filterNotEmpty()

    override fun part1() : Any = run(25).map { it.value }.sum()
    override fun part2() : Any = run(75).map { it.value }.sum()

    private fun run(times: Int) = (1..times).fold(initialRocks.groupingBy { it }.eachCount().mapValues { it.value.toLong() }) { acc, _ ->
        val next = mutableMapOf<String,Long>().withDefault { 0 }
        acc.forEach { (it, value) ->
            when {
                it == "0" -> {
                    next["1"] = next.getValue("1") + value
                }
                it.length % 2 == 0 -> {
                    val left = it.take(it.length / 2).toLong().toString()
                    val right = it.drop(it.length / 2).toLong().toString()
                    next[left] = next.getValue(left) + value
                    next[right] = next.getValue(right) + value
                }
                else -> {
                    val key = (it.toLong() * 2024).toString()
                    next[key] = next.getValue(key) + value
                }
            }
        }
        next
    }
}