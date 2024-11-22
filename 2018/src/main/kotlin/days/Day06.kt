package days

class Day06 : Day(6) {

    private val inputs = inputList.map { it.split(", ").let { (a, b) -> (a.toInt() to b.toInt()) } }
    private val coords = (inputs.minOf{ it.first } - 1 .. inputs.maxOf { it.first } + 1 ). flatMap { x ->
        (inputs.minOf { it.second } - 1..inputs.maxOf { it.second } + 1).map { y ->
            x to y
        }
    }

    override fun part1() : Any {
        return coords.mapNotNull { coord ->
            inputs.minByWithDuplicates { coord.manhattanDistanceTo(it) }.let { if (it.size == 1) it[0] else null }
        }.groupingBy { it }.eachCount().maxOf { it.value }
    }

    override fun part2() : Any {
        return coords.count {  coord ->
            inputs.sumOf { it.manhattanDistanceTo(coord) } < 10000
        }
    }

    private fun Pair<Int,Int>.manhattanDistanceTo(other: Pair<Int,Int>): Int {
        return (kotlin.math.abs(this.first - other.first) + kotlin.math.abs(this.second - other.second))
    }

    private inline fun <T, R: Comparable<R>> Iterable<T>.minByWithDuplicates(transform: (T) -> R): List<T> {
        val results = this.associateWith { transform(it) }
        val min = results.minOf { it.value }
        return results.filter { it.value == min }.keys.toList()
    }
}