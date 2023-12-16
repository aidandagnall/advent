package days

import util.plus

class Day16 : Day(16) {

    val mirrors = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c in listOf('-', '|', '/', '\\')) (x to y) to c else null
        }.filterNotNull()
    }.toMap()

    val minX = 0
    val minY = 0
    val maxY = inputList.size - 1
    val maxX = inputList.first().length - 1

    private fun simulate(start: Pair<Int,Int>, dir: Char): Int {
        val energized = mutableSetOf<Pair<Int,Int>>()
        val visited = mutableSetOf<Pair<Pair<Int,Int>,Char>>()
        val beams = mutableListOf(start to dir)
        var first = true

        while(beams.isNotEmpty()) {
            val (point, dir) = beams.removeFirst()

            energized.add(point)

            val next = getNext(point, dir)
            if (!first) {
                if (next.first !in minX..maxX || next.second !in minY..maxY) {
                    continue
                }
            }
            first = false

            if (next to dir in visited) continue

            visited.add(next to dir)
            when {
                mirrors[next] == '-' && dir in listOf('U', 'D') -> {
                    beams.add(next to 'L')
                    beams.add(next to 'R')
                }
                mirrors[next] == '|' && dir in listOf('L', 'R') -> {
                    beams.add(next to 'U')
                    beams.add(next to 'D')
                }
                mirrors[next] == '/' -> {
                    when (dir) {
                        'L' -> beams.add(next to 'D')
                        'R' -> beams.add(next to 'U')
                        'U' -> beams.add(next to 'R')
                        else -> beams.add(next to 'L')
                    }
                }
                mirrors[next] == '\\' -> {
                    when (dir) {
                        'L' -> beams.add(next to 'U')
                        'R' -> beams.add(next to 'D')
                        'U' -> beams.add(next to 'L')
                        else -> beams.add(next to 'R')
                    }
                }
                else -> beams.add(next to dir)
            }
        }
        return energized.count() - 1
    }

    override fun part1() : Any = simulate((-1 to 0), 'R')

    private fun getNext(point: Pair<Int,Int>, dir: Char): Pair<Int,Int> {
        return when(dir) {
            'L' -> point + (-1 to 0)
            'R' -> point + (1 to 0)
            'U' -> point + (0 to -1)
            else -> point + (0 to 1)
        }
    }

    override fun part2() : Any {
        return ((minX..maxX).map {
            listOf((it to -1) to 'D', (it to maxY + 1) to 'U')
        } + (minY..maxY).map {
            listOf((-1 to it) to 'R', (maxY + 1 to it) to 'L')
        }).flatten().maxOf {  (start, dir) ->
            simulate(start, dir)
        }
    }
}