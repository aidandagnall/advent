package days

import util.lcm

class Day08 : Day(8) {

    private val directions = inputList.first()
    private val map = inputList.drop(2).associate {
        """([\dA-Z]{3}) = \(([\dA-Z]{3}), ([\dA-Z]{3})\)""".toRegex().matchEntire(it)!!.groupValues.let { (_, a, b, c) ->
            a to (b to c)
        }
    }
    override fun part1() : Any {
        var current = "AAA"
        var index = 0
        while(current != "ZZZ") {
            val dir = directions[index % directions.length]
            current = map[current]!!.let { if (dir == 'L') it.first else it.second }
            index += 1
        }
        return index
    }

    override fun part2() : Any {
        return map.filterKeys { it.last() == 'A' }.map {
            var current = it.key
            var index = 0
            while(current.last() != 'Z') {
                current = if (directions[index % directions.length] == 'L') map[current]!!.first else map[current]!!.second
                index += 1
            }
            index
        }.fold(1L) { acc, i ->
            acc.lcm(i.toLong())
        }
    }
}