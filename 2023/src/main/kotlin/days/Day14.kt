package days

import util.filterNotEmpty

class Day14 : Day(14) {
    enum class Direction { North, West, South, East}

    private val rocks = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == '.') null
            else (x to y) to c
        }.filterNotNull()
    }.toMap()

    private fun platform(dir: Direction) = when(dir) {
        Direction.North -> 0
        Direction.West -> 0
        Direction.South -> inputList.filterNotEmpty().size
        Direction.East -> inputList.filterNotEmpty().first().length
    }

    override fun part1() : Any = getLoad(roll(rocks, Direction.North))


    private fun roll(rocks: Map<Pair<Int,Int>, Char>, dir: Direction): Map<Pair<Int,Int>, Char> {
        return rocks.filter { it.value == 'O' }.toList()
            .sortedBy { (coord, _) ->
                when(dir) {
                    Direction.North -> coord.second
                    Direction.West-> coord.first
                    Direction.South -> -coord.second
                    Direction.East -> -coord.first
                }
            }.fold(rocks) { acc, (coord, _) ->
                val next = when(dir) {
                    Direction.North -> (coord.second - 1 downTo platform(Direction.North)).takeWhile {
                        coord.first to it !in acc
                    }.lastOrNull()?.let { coord.first to it } ?: coord
                    Direction.East -> (coord.first + 1 ..< platform(Direction.East)).takeWhile {
                        it to coord.second !in acc
                    }.lastOrNull()?.let { it to coord.second } ?: coord
                    Direction.South -> (coord.second + 1 ..< platform(Direction.South)).takeWhile {
                        coord.first to it !in acc
                    }.lastOrNull()?.let { coord.first to it } ?: coord
                    Direction.West -> (coord.first  - 1 downTo platform(Direction.West)).takeWhile {
                        it to coord.second !in acc
                    }.lastOrNull()?.let { it to coord.second } ?: coord
                }
                acc.minus(coord).plus(next to 'O')
        }
    }

    private fun getLoad(rocks: Map<Pair<Int,Int>, Char>, dir: Direction = Direction.North): Long =
        rocks.filter { it.value == 'O' }
            .keys
            .sumOf { (x, y) ->
                when(dir) {
                    Direction.North -> platform(Direction.South) - y
                    Direction.East -> x + 1
                    Direction.South -> y + 1
                    Direction.West-> platform(Direction.East) - x
                }
            }.toLong()
    val cache = mutableMapOf<Map<Pair<Int,Int>, Char>, Int>()

    private fun runCycle(rocks: Map<Pair<Int,Int>, Char>): Map<Pair<Int,Int>, Char> =
        Direction.entries.fold(rocks) { acc, dir ->  roll(acc, dir) }

    override fun part2() : Any {
        return (0..1_000_000_000).fold(rocks) { acc, i ->
            println(i)
            if (acc in cache) {
                println("CACHE HIT ${cache[acc]} -> $i")
                return (1.. ((1_000_000_000 - cache[acc]!!) % (cache[acc]!! - i))).fold(acc) { acc2, j ->
                    println(j)
                    runCycle(acc2)
                }.let { getLoad(it) }
            }
            Direction.entries.fold(acc) { acc2, dir ->
                roll(acc2, dir)
            }.also {
                cache[acc] = i
            }
        }.let { getLoad(it) }
    }

    fun printGrid(rocks: Map<Pair<Int,Int>, Char>) {
        println((rocks.keys.minOf { it.second } .. rocks.keys.maxOf { it.second }).joinToString("\n") { y ->
            ((rocks.keys.minOf { it.first })..rocks.keys.maxOf { it.first }).joinToString("") { x ->
                if (x to y in rocks) rocks[x to y]!!.toString() else '.'.toString()
            }
        })

        println()
    }
}