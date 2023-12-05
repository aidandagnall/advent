package days

import kotlin.math.pow

class Day04 : Day(4) {
    private val games = inputList.filter { it != "" }.associate {
        it.split(":", "|").let { (number, winnersRaw, yoursRaw) ->
            val winners = winnersRaw.split(" ").filter { it != "" }.map { it.toInt() }
            val yours = yoursRaw.split(" ").filter { it != "" }.map { it.toInt() }

            number.removePrefix("Card").trim().toInt() to winners.count { it in yours }
        }
    }

    override fun part1() : Any = games.values.sumOf {
            if (it == 0) 0
            else 2.0.pow(it - 1).toInt()
        }

    override fun part2() : Any = games.toList().fold(mapOf<Int,Int>()) { acc, (number, matches) ->
            val changes: List<Pair<Int,Int>> = (1..matches).map { count ->
                number + count to (acc.getOrDefault(number + count, 0) + acc.getOrDefault(number, 0) + 1)
            }
            acc.plus(number to acc.getOrDefault(number, 0) + 1).plus(changes)
        }.values.sum()
}