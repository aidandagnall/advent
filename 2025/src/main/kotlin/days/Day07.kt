package days

import util.parseGrid
import util.plus

class Day07 : Day(7) {
    val start = inputList.parseGrid { pair, ch ->  if (ch == 'S') pair else null }.first()
    val splitters = inputList.parseGrid { pair, ch -> if (ch == '^') pair else null }.toSet()

    override fun part1() : Any {
        var activeBeams = listOf(start)
        var count = 0

        while (activeBeams.minOf { it.second } < splitters.maxOf { it.second}) {
            val next = activeBeams.flatMap {
                val down = it + (0 to 1)
                if (down in splitters) {
                    count += 1
                    return@flatMap listOf(down + (1 to 0), down + (-1 to 0))
                }
                return@flatMap listOf(down)
            }
            activeBeams = next.distinct()
        }
        return count
    }

    override fun part2() : Any {
        var activeBeams = mapOf(start to 1L)

        while (activeBeams.minOf { it.key.second } < splitters.maxOf { it.second}) {
            val next = activeBeams.flatMap {
                val down = it.key + (0 to 1)
                if (down in splitters) {
                    return@flatMap listOf(down + (1 to 0) to it.value, down + (-1 to 0) to it.value)
                }
                return@flatMap listOf(down to it.value)
            }
            val t = (next.map { it.first }.distinct().map { k -> k to next.filter { k == it.first }.sumOf { it.second.toLong() } })
            activeBeams = t.toMap()
        }
        return activeBeams.map { it.value }.sum()
    }
}