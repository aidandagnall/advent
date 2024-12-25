package days

import util.transpose

class Day25 : Day(25) {
    private val input = inputGroupedList.map { it.type() to it }
        .map { (t, it) -> t to it.map { it.toList() }.transpose() }
        .map { (t, it) -> t to it.map{ it.count { it == '#' } - 1 } }

    enum class Input { LOCK, KEY }
    private fun List<String>.type(): Input = when {
        first().all { it == '#' } -> Input.LOCK
        last().all { it == '#' } -> Input.KEY
        else -> error("Invalid group")
    }

    override fun part1() : Any =
        input.filter { it.first == Input.LOCK }.sumOf { (_, l) ->
            input.filter { it.first == Input.KEY }.count { (_, k) ->
                l.zip(k).all { (a, b) -> a + b <= 5 }
            }
        }

    override fun part2() : Any = "Merry Christmas"
}