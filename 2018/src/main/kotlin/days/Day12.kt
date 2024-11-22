package days

class Day12 : Day(12) {
    private val initial = inputList.first()
        .removePrefix("initial state: ")
        .mapIndexed { i, it -> i to it }
        .toMap()
    private val notes = inputList.drop(2).associate {
        it.take(5).toList() to it.last()
    }

    override fun part1() : Any =
        (1..20).fold(initial) { acc, _ ->
            acc.generateNext()
        }.entries.sumOf { (k, v) -> if (v == '#') k else 0 }

    override fun part2() : Any {
        val cache = mutableMapOf<String, Pair<Int,Long>>()

        return (0..50_000_000_000).fold(initial) { acc, i ->
            val str = acc.entries.sortedBy { it.key }
                .map { it.value }
                .joinToString("")

            if (str in cache) {
                return 50_000_000_000 * str.count { it == '#' }
            }

            cache[str] = (acc.keys.min() to i)
            acc.generateNext()
        }.entries.sumOf { (k, v) -> if (v == '#') k else 0 }
    }

    private fun Map<Int, Char>.generateNext(): Map<Int, Char> {
        val min = this.minOf { (k, v) -> if (v == '#') k else Int.MAX_VALUE }
        val max = this.maxOf { (k, v) -> if (v == '#') k else Int.MIN_VALUE}

        return (min - 2..max + 2).associateWith {
            notes[
                listOf(
                    this.getOrDefault(it - 2, '.'),
                    this.getOrDefault(it - 1, '.'),
                    this.getOrDefault(it, '.'),
                    this.getOrDefault(it + 1, '.'),
                    this.getOrDefault(it + 2, '.'),
                )
            ] ?: '.'
        }
    }
}