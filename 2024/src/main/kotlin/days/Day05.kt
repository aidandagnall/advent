package days

class Day05 : Day(5) {

    private val ordering = inputGroupedList.first()
        .map { it.split("|").let { (a, b) -> a.toInt() to b.toInt() } }
        .toSet()
    private val updates = inputGroupedList.last()
        .map { it.split(",").map(String::toInt) }

    override fun part1() : Any =
        updates
            .filter { it.isValid() }
            .sumOf { it[it.count() / 2].toInt() }

    override fun part2() : Any =
        updates
            .filterNot { it.isValid() }
            .map { it.sortedWith(::sort)}
            .sumOf { it[it.count() / 2].toInt() }

    private fun List<Int>.isValid() = ordering.all { (first, last) ->
        indexOf(first) < indexOf(last).let { if (it == -1) Int.MAX_VALUE else it }
    }

    private fun sort(a: Int, b: Int): Int = when {
        a to b in ordering -> -1
        b to a in ordering -> 1
        else -> 0
    }
}