package days

class Day02 : Day(2) {
    val ids = inputList.first().split(",")
        .map { it.split("-") }
        .flatMap { (a, b) -> a.toLong()..b.toLong() }
        .map { it.toString() }

    override fun part1() : Any = ids.filter {
            it.length % 2 == 0 && it.isRepeating(it.length / 2)
        }.sumOf { it.toLong() }

    override fun part2() : Any = ids.filter {
            (it.length / 2 downTo 1).any { size ->
                it.isRepeating(size)
            }
        }.sumOf { it.toLong() }

    private fun String.isRepeating(size: Int): Boolean = this == substring(0, size).repeat(length / size)
}