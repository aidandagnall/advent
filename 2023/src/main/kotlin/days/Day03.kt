package days

import util.plus

class Day03 : Day(3) {

    val symbols = inputList.mapIndexed { y, it ->
        it.mapIndexed { x, c ->
            if (!c.isDigit() && c != '.')
                (x to y) to c
            else null
        }.filterNotNull()
    }.flatten().toMap()

    val numbers = inputList.mapIndexed { y, it ->
        val n = mutableListOf<Pair<List<Pair<Int,Int>>,Int>>()
        var bufferN = 0
        var bufferCoords = mutableListOf<Pair<Int,Int>>()

        it.forEachIndexed { x, c ->
            if (c.isDigit()) {
                bufferN *= 10
                bufferN += c.digitToInt()
                bufferCoords.add(x to y)
            }
            else {
                if (bufferN != 0) {
                    n += bufferCoords to bufferN
                    bufferN = 0
                    bufferCoords = mutableListOf()
                }
            }
        }

        if (bufferN != 0) {
            n += bufferCoords to bufferN
            bufferN = 0
            bufferCoords = mutableListOf()
        }

        n.toList()
    }.flatten()

    override fun part1() : Any {
        return numbers
            .filter { (coords, _) ->
                coords.any {
                    (-1..1).any {x ->
                        (-1..1).any { y ->
                            it.first + x to it.second + y in symbols.keys
                        }
                    }
                }
            }.sumOf { it.second }

    }

    override fun part2() : Any {
        return symbols.filter { (_, v) -> v == '*' }
            .map { (k, _) ->
                numbers.filter {
                    (-1..1).any {x ->
                        (-1..1).any { y ->
                            k + (x to y) in it.first
                        }
                    }
                }
            }.filter { it.count() == 2 }
            .sumOf { (a, b) -> a.second * b.second }
    }
}