package days

import util.filterNotEmpty
import util.repeat
import util.separate

class Day12 : Day(12) {

    private val records = inputList.map {
        it.split(" ").let { (gears, groups) ->
            gears to groups.split(",").filterNotEmpty().map { it.toInt() }
        }
    }

    override fun part1() : Any = records.sumOf { (gears, groups) ->
        find(gears.separate('.'), groups)
    }

    override fun part2() : Any = records
        .map { (gears, groups) ->
            (1..5).joinToString("?") { gears }.separate('.') to groups.repeat(5)
        }.sumOf { (gears, groups) -> find(gears, groups) }

    private val cache = mutableMapOf<Pair<List<String>,List<Int>>, Long>()

    private fun find(gears: List<String>, groups: List<Int>): Long = when {
        gears to groups in cache -> cache[gears to groups] ?: 0
        gears.isEmpty() && groups.isEmpty() -> 1
        gears.isEmpty() -> 0
        groups.isEmpty() && gears.any { '#' in it } -> 0
        '?' in gears.first() -> listOf('.', '#').sumOf {
            find(gears.first().replaceFirst('?', it).separate('.') + gears.drop(1),
                groups
            )
        }
        else -> if (gears[0].length == groups.first()) find(gears.drop(1), groups.drop(1)) else 0
    }.also { cache[gears to groups] = it }
}