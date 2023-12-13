package days

import util.transpose

class Day13 : Day(13) {

    private val patterns = inputGroupedList.map { block ->
        block
    }


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
        block.mapIndexed { i, it -> i to it }
            .windowed(2)
            .filter { (a, b) -> (a.second.compareTo(b.second) == 0) }
            .filter { it.first().first + 1 != ignore }
            .firstOrNull { (a, _) ->
                block.take(a.first + 1).reversed().zip(block.drop(a.first + 1)).all { (a, b) ->
                    a == b
                }
            }?.first()?.first?.let {
                it + 1
            } ?: 0
}