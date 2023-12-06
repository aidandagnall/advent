package days

class Day05 : Day(5) {
    data class Layer(val mappers: List<Mapper>) {
        fun map(input: Long): Long = mappers.find { input in it.srcRange }?.map(input) ?: input
        fun subRanges(start: Long, size: Long): List<Pair<Long, Long>> {
            val breakpoints = mappers.flatMap { listOf(it.srcRange.first, it.srcRange.last) } + listOf(start, start + size)

            return breakpoints.asSequence().sorted()
                .dropWhile { it < start }.takeWhile { it <= start + size}
                .windowed(2)
                .map { (first, last) ->
                    mappers.find { first in it.srcRange }?.let { it.map(first) to (last - first) } ?: (first to (last - first))
                }.toList()
        }
    }
    data class Mapper(val dest: Long, val src: Long, val size: Long) {
        val srcRange: LongRange = src ..< src + size
        fun map(input: Long): Long = input - src + dest
    }

    private val seeds = inputList.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
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

    override fun part2() : Any = seeds.chunked(2).minOf { (seedStart, seedSize) ->
            layers.fold(listOf(seedStart to seedSize)) { acc, layer ->
                acc.flatMap { (start, size) ->
                    layer.subRanges(start, size)
                }
            }.minOf { it.first }
        }
}

