package days

import util.transpose

class Day13 : Day(13) {

    val patterns = inputGroupedList.map { block ->
        block
    }

    private fun findRows(block: List<String>, ignore: Int? = null): Int {
        val matches = block.mapIndexed { i, it -> i to it }.windowed(2).filter { (a, b) ->
            (a.second.compareTo(b.second) == 0)
        }
        if (matches.isEmpty()) return 0

        return matches.filter { it.first().first + 1 != ignore }.firstOrNull { (a, b) ->
            val index = a.first
            block.take(index + 1).reversed().zip(block.drop(index + 1)).all { (a, b) ->
                a == b
            }
        }?.first()?.first?.let {
            it + 1
        } ?: 0
    }

    private fun findCols(block: List<String>, ignore: Int? = null): Int {
        if (block.count{ it == "....#.." } == 2) {
            println("found")
            println(block.map { it.toList() }.transpose().map { it.joinToString("").reversed() }.joinToString("\n"))

            println("  ")

        }
        return findRows(block.map { it.toList() }.transpose().map { it.joinToString("").reversed() }, ignore)
    }

    override fun part1(): Any {
        return patterns.sumOf { block ->
            val rows = findRows(block)
            val cols = findCols(block)

            cols + rows * 100
        }
    }

    override fun part2(): Any {
        return patterns.sumOf { block ->
            val origRows = findRows(block)
            val origCols = findCols(block)

            val combos = mutableListOf<List<String>>()

            block.forEachIndexed { i, line ->
                line.forEachIndexed { j, c ->
                    combos.add(
                        block.take(i) +
                                listOf(line.take(j) + (if (c == '#') "." else "#") + line.drop(j + 1)) +
                                block.drop(i + 1)
                    )
                }
            }

//            println(combos)

            // 21577 too low
            // 23035 too low

            combos.asSequence().map {
//                println("ORIG: (row -> $origRows, col -> $origCols)")
//                println(block.joinToString("\n"))
//                println("NEW: ")
//                println(it.joinToString("\n"))
                val newRows = findRows(it, origRows)
//                    .let { if (it == origRows) 0 else it }
                val newCols = findCols(it, origCols)
//                    .let { if (it == origCols) 0 else it }
//                println("new row -> $newRows, new col -> $newCols")
//                println("  ")
//                println("  ")
//                println("  ")
//                println("  ")

//                if (newRows == origRows && newCols == origCols) null
                if (newRows == 0 && newCols == 0) null
                else newCols + newRows * 100L
            }.filterNotNull().firstOrNull() ?: println("MISSING").let { 0 }
//            combos.sumOf {
//                println()
//                println(it.joinToString("\n"))
//                val newRows = findRows(it).let { if (it == origRows) 0 else it }
//                val newCols = findCols(it).let { if (it == origCols) 0 else it }
//
//                if (newRows * 100 + newCols != origRows * 100 + origCols && newCols + newRows * 100 != 0) {
//                    println("row -> $newRows, col -> $newCols")
//                    newCols + newRows * 100
//                } else 0
//            } / 2
        }
    }
}