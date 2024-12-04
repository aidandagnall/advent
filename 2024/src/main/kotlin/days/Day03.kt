package days

class Day03 : Day(3) {
    private val mulRegex = """mul\((\d+),(\d+)\)""".toRegex()
    private val doDontRegex = """(do\(\)|don't\(\))""".toRegex()

    private val input = inputList.joinToString("")

    private val muls = input.let { mulRegex.findAll(it) }
    private val dontRanges = input.let { doDontRegex.findAll(it) }
        .map { it.value to it.range.first }
        .let { listOf("do()" to 0) + it + ("do()" to input.length) }
        .windowed(2)
        .mapNotNull { (a, b) -> if (a.first == "don't()") a.second..<b.second else null }

    override fun part1(): Any =
        muls.sumOf { it.groupValues.let { (_, a, b) -> a.toInt() * b.toInt() } }

    override fun part2(): Any =
        muls.filterNot { mul -> dontRanges.any { mul.range.first in it } }
            .sumOf { mul -> mul.groupValues.let { (_, a, b) -> a.toInt() * b.toInt() } }
}