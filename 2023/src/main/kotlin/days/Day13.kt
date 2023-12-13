package days

import util.transpose

class Day13 : Day(13) {

    val patterns = inputGroupedList.map {  block ->
        block
    }

    private fun findRows(block: List<String>): Int {
        println("RUNNING:")
        println(block.joinToString("\n"))
        val matches = block.windowed(2).filter { (a, b) -> a == b }
        println("match: $matches")
        if (matches.isEmpty()) return 0

//        return matches.first {
//            val index = block.indexOf(it.first())
//            block.take(index + 1).reversed().zip(block.drop(index + 1)).all {  (a, b) ->
//                println("Combaring $a and $b")
//                a == b
//            }
//        }.sumOf { block.indexOf(it.first()) + 1 }

        val index = block.indexOf(matches.first().first())
        return if (block.take(index + 1).reversed().zip(block.drop(index + 1)).all {  (a, b) ->
            println("Combaring $a and $b")
            a == b
        }) {
            println("full match found -> ${index + 1}")
            index + 1
        } else {
            println("no match found")
            0
        }
    }

    private fun findCols(block: List<String>): Int {
        return findRows(block.map { it.toList() }.transpose().map { it.joinToString("").reversed() })
    }

    override fun part1() : Any {
        return patterns.sumOf {  block ->
            if (findRows(block) > 0) findRows(block) * 100
            else findCols(block)
//            findCols(block) + findRows(block) * 100
        }
    }

    override fun part2() : Any {
        return 0
    }
}