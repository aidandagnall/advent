package days

import util.transpose

class Day13 : Day(13) {

    private val patterns = inputGroupedList

    private fun findCols(block: List<String>, ignore: Int? = null): Int = block.map {
            it.toList()
        }.transpose()
        .map { it.joinToString("") }
        .let { findRows(it, ignore) }

    override fun part1(): Any = patterns.sumOf { block -> findRows(block) * 100 + findCols(block) }

    override fun part2(): Any = patterns.sumOf { block ->
        val origRows = findRows(block)
        val origCols = findCols(block)

        block.flatMapIndexed { i, line ->
            line.mapIndexed { j, c ->
                block.take(i) +
                    listOf(line.take(j) + (if (c == '#') "." else "#") + line.drop(j + 1)) +
                    block.drop(i + 1)
            }
        }.asSequence()
        .map {
            findRows(it, origRows) * 100 + findCols(it, origCols)
        }.filter { it > 0 }.firstOrNull() ?: println("MISSING").let { 0 }
    }

    private fun findRows(block: List<String>, ignore: Int? = null): Int =
        block.asSequence().withIndex()
            .windowed(2)
            .filter { (a, b) -> a.value == b.value }
            .filter { (a, _) -> a.index + 1 != ignore }
            .firstOrNull { (a, _) ->
                block.take(a.index + 1).reversed().zip(block.drop(a.index + 1)).all { (a, b) ->
                    a == b
                }
            }?.let { (a, _) -> a.index + 1} ?: 0
}