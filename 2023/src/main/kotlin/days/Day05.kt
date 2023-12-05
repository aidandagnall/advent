package days

import kotlin.math.min

class Day05 : Day(5) {

    val seeds = inputList.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
    val maps = inputGroupedList.drop(1).map {
        it.drop(1).map {
            it.split(" ").let { (dest, src, len) ->
                Triple(dest.toLong(), src.toLong(), len.toLong())
            }
        }
    }
    override fun part1() : Any {
        val seeds = inputList.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
        return seeds.minOf { num ->
            maps.fold(num) { acc, map ->
                map.firstOrNull { (dest, src, len) ->
                    acc in (src..<src + len)
                }?.let { (dest, src, len) ->
                    acc + (dest - src)
                } ?: acc
            }
        }
    }

    fun createMappingRanges(seed: Long, range: Long, mappers: List<Triple<Long,Long,Long>>): List<Pair<Long,Long>> {
        val out = mutableListOf<Pair<Long,Long>>()

        var currentSeed = seed


        if (seed + range < mappers.minOf { it.second }) {
            return listOf(seed to range)
        }

        val maps = mappers
            .filter { (_, src, len) -> currentSeed <= src + len}
            .sortedBy { it.second }.toMutableList()


        var currentEnd = mappers.first().second

        println(" curr-> $currentSeed")
        println(" end-> $currentEnd")
        println(maps)

        while (currentEnd < seed + range) {

            if (currentSeed in maps.first().let { (_, s, r) -> s..s + r }) {
                out.add(maps.first().first to currentEnd - currentSeed)
                currentSeed = maps.first().second + maps.first().third
                maps.removeFirst()
            } else {
                out.add(currentSeed to currentEnd - currentSeed)
                currentSeed = currentEnd
            }
            currentEnd = min(maps.first().second, seed + range)
        }

        println(" out -> $out")
        return out
    }

    override fun part2() : Any {
        val seedRanges = inputList.first().removePrefix("seeds: ").split(" ").chunked(2).map { (a, b) -> a.toLong() to b.toLong() }

        val ranges = seedRanges.map { (a,b) -> a..< a + b }

        return (0..maps.last().maxOf { it.first }).first { num ->
            maps.fold(num) { acc, map ->
                map.firstOrNull { (dest, src, len) ->
                    acc in (dest..<dest + len)
                }?.let { (dest, src, len) ->
                    acc - dest  + src
                } ?: acc
            }.let { seed ->
              ranges.any { seed in it }
            }
        }
    }
}