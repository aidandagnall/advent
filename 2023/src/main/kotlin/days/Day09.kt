package days

class Day09 : Day(9) {
    private val histories = inputList.map { it.split(" ").map { it.toInt() } }
    private fun getNext(values: List<Int>): Int =
        generateSequence(values) {
            it.windowed(2).map { (a, b) -> b - a }
        }.takeWhile { !it.all { it == 0 } }.sumOf { it.last() }

    override fun part1() : Any = histories.sumOf(::getNext)
    override fun part2() : Any = histories.map { it.reversed() }.sumOf(::getNext)
}