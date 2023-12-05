package days

import kotlin.math.min

class Day05 : Day(5) {
    data class Mapper(val dest: Long, val src: Long, val size: Long) {
        val srcRange: LongRange
            get() = src..<src + size

        val destRange: LongRange
            get() = dest ..< dest + size

        fun map(input: Long): Long = if (input in srcRange) input - src + dest else input

        fun subRanges(start: Long, end: Long): List<LongRange> {
            if (start > src + size || end < src) return emptyList()

            if (start < src && src + size < end) return listOf(start..< src, destRange, src + size ..< end)

            else return emptyList()
//            if ()
        }
    }

    private val seeds = inputList.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
    private val maps = inputGroupedList.drop(1).map { group ->
        group.drop(1).map {
            it.split(" ").let { (dest, src, len) ->
                Mapper(dest.toLong(), src.toLong(), len.toLong())
            }
        }
    }

    // 388071289
    override fun part1() : Any {
        return seeds.minOf { num ->
            maps.fold(num) { acc, map ->
                map.firstOrNull { (_, src, len) ->
                    acc in (src..<src + len)
                }?.let { (dest, src, _) ->
                    acc + (dest - src)
                } ?: acc
            }
        }
    }

    // 84206669
    override fun part2() : Any {
        return 0
        val maps = maps.map { it.map { (dest, src, len) -> dest..< dest + len to src} }

        val seedRanges = inputList.first().removePrefix("seeds: ").split(" ")
            .chunked(2)
            .map { (a, b) -> a.toLong() ..< a.toLong() + b.toLong() }

        return (0..maps.last().maxOf { it.first.last }).first { num ->
            maps.reversed().fold(num) { acc, map ->
                map.firstOrNull { (range, src) ->
                    acc in range
                }?.let { (dest, src) ->
                    acc - dest.first + src
                } ?: acc
            }.let { seed ->
                seedRanges.any { seed in it }
            }
        }
    }
}
