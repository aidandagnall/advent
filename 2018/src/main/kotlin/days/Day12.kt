package days

class Day12 : Day(12) {
    private val initial = inputList.first().removePrefix("initial state: ").mapIndexed { i, it ->
        i to it
    }.toMap()
    private val notes = inputList.drop(2).map {
        it.take(5).map { it } to it.last()
    }.toMap()

    override fun part1() : Any {
        return (1..20).fold(initial) { acc, i ->

            val min = acc.minOf { (k, v) -> if (v == '#') k else Int.MAX_VALUE }
            val max = acc.maxOf { (k, v) -> if (v == '#') k else Int.MIN_VALUE}
//            println("$i: ${(-3..34).map { acc[it] ?: '.' }.joinToString("")}")

            (min - 2..max + 2).associateWith {
                notes.getOrDefault(
                    listOf(
                        acc.getOrDefault(it - 2, '.'),
                        acc.getOrDefault(it - 1, '.'),
                        acc.getOrDefault(it, '.'),
                        acc.getOrDefault(it + 1, '.'),
                        acc.getOrDefault(it + 2, '.'),
                    ), '.'
                )
            }
        }.entries.sumOf { (k, v) -> if (v == '#') k else 0 }
    }

    // 3349999993068 too low

    // 3349999993134 ??

    // 3349999993202 ??

    // 3350000002965 too high
    // 3350000003032 too high

    override fun part2() : Any {
        // Pair<Int,Long> = first digit to iteration
        val cache = mutableMapOf<String, Pair<Int,Long>>()

        return (0..50_000_000_000).fold(initial) { acc, i ->
            println()
            println(acc.entries.sortedBy { it.key }.map { it.value }.joinToString(""))
            println("first -> ${acc.keys.min()}")
            println("$i -> ${acc.size}")
            val str = acc.entries.sortedBy { it.key }.map { it.value }.joinToString("")
            if (str in cache) {
                val first = cache[str]!!.second
                val repeating = i - first


                val finalIndex = (50_000_000_000 - first) % repeating

                println("loop found at $first -> $i")

                // you need
                return (50_000_000_000 - first) * str.count { it == '#' } +
                        // this needs to calculate the score of the inital, not use the iteration number
                        cache[str]!!.second

//                return cache.firstNotNullOf { (k, v) -> if (v == finalIndex) k else null }.entries.sumOf { (k, v) -> if (v == '#') k else 0 }
            }
            cache[str] = (acc.keys.min() to i)

            val min = acc.minOf { (k, v) -> if (v == '#') k else Int.MAX_VALUE }
            val max = acc.maxOf { (k, v) -> if (v == '#') k else Int.MIN_VALUE}

            (min - 2..max + 2).associateWith {
                notes[
                    listOf(
                        acc.getOrDefault(it - 2, '.'),
                        acc.getOrDefault(it - 1, '.'),
                        acc.getOrDefault(it, '.'),
                        acc.getOrDefault(it + 1, '.'),
                        acc.getOrDefault(it + 2, '.'),
                    )
                ]!!
            }
        }.entries.sumOf { (k, v) -> if (v == '#') k else 0 }
    }
}