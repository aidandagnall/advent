package days

class Day04 : Day(4) {

    private val input = inputList
        .map { line ->
            line.split(",")
                .map { it.split("-") }
                .map { (a, b) -> a.toInt()..b.toInt() }
        }.map { (a, b) ->
            a to b
        }

    override fun part1() : Any {
        return input.count {(a, b) ->
            (a.first in b && a.last in b) || (b.first in a && b.last in a)
        }
    }

    override fun part2() : Any {
        return input.count {(a, b) ->
            (a.first in b || a.last in b) || (b.first in a || b.last in a)
        }
    }
}