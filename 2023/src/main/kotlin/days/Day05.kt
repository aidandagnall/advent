package days

class Day05 : Day(5) {
    data class Layer(val mappers: List<Mapper>) {
        fun map(input: Long): Long = mappers.find { input in it }?.map(input) ?: input
        fun subRanges(start: Long, size: Long): List<Pair<Long, Long>> {
            val breakpoints = mappers.flatMap { listOf(it.srcRange.first, it.srcRange.last) } + listOf(start, start + size)

            return breakpoints.asSequence().sorted()
                .dropWhile { it < start }.takeWhile { it <= start + size}
                .windowed(2)
                .map { (first, last) ->
                    mappers.find { first in it }?.let { it.map(first) to (last - first) } ?: (first to (last - first))
                }.toList()
        }
    }
    data class Mapper(val dest: Long, val src: Long, val size: Long) {
        val srcRange: LongRange = src ..< src + size
        fun map(input: Long): Long = input - src + dest
        operator fun contains(other: Long): Boolean = other in srcRange
    }

    private val seeds = inputList.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
    private val seedRanges = inputList.first().removePrefix("seeds: ").split(" ").chunked(2)
        .map { (a, b) -> a.toLong()to b.toLong() }
    private val layers = inputGroupedList.drop(1).map { group ->
        Layer(
            group.drop(1).map {
                it.split(" ").let { (dest, src, len) ->
                    Mapper(dest.toLong(), src.toLong(), len.toLong())
                }
            }
        )
    }

    override fun part1() : Any = seeds.minOf { num ->
            layers.fold(num) { acc, layer ->
                layer.map(acc)
            }
        }

    override fun part2() : Any = seedRanges.minOf {
            layers.fold(listOf(it)) { acc, layer ->
                acc.flatMap { (start, size) ->
                    layer.subRanges(start, size)
                }
            }.minOf { it.first }
        }
}

