package days

import util.filterNotEmpty

class Day14 : Day(14) {

    val rocks = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == '.') null
            else (x to y) to c
        }.filterNotNull()
    }.toMap()

    val platforms = mapOf(
        'N' to 0,
        'W' to 0,
        'S' to inputList.filterNotEmpty().size,
        'E' to inputList.filterNotEmpty().first().length,
    )

    override fun part1() : Any = getLoad(run(rocks, 'N'), 'N')


    private fun run(rocks: Map<Pair<Int,Int>, Char>, dir: Char): Map<Pair<Int,Int>, Char> {
        return rocks.filter { it.value == 'O' }.toList()
            .sortedBy { (coord, _) ->
                when(dir) {
                    'N' -> coord.second
                    'E' -> 100000 - coord.first
                    'S' -> 100000 - coord.second
                    else -> coord.first
                }
            }.fold(rocks) { acc, (coord, _) ->
                val next = when(dir) {
                    'N' -> (coord.second - 1 downTo platforms['N']!!).takeWhile {
                        coord.first to it !in acc
                    }.lastOrNull()?.let { coord.first to it } ?: coord
                    'E' -> (coord.first + 1 ..< platforms['E']!!).takeWhile {
                        it to coord.second !in acc
                    }.lastOrNull()?.let { it to coord.second } ?: coord
                    'S' -> (coord.second + 1 ..< platforms['S']!!).takeWhile {
                        coord.first to it !in acc
                    }.lastOrNull()?.let { coord.first to it } ?: coord
                    else -> (coord.first  - 1 downTo platforms['W']!!).takeWhile {
                        it to coord.second !in acc
                    }.lastOrNull()?.let { it to coord.second } ?: coord
                }
                acc.minus(coord).plus(next to 'O')
        }
    }

    private fun getLoad(rocks: Map<Pair<Int,Int>, Char>, dir: Char): Long = rocks.filter { it.value == 'O' }
        .keys
        .sumOf { (x, y) ->
            when(dir) {
                'N' -> platforms['S']!! - y
                'E' -> x + 1
                'S' -> y + 1
                else -> platforms['E']!! - x
            }
        }.toLong()
    val cache = mutableMapOf<Map<Pair<Int,Int>, Char>, Int>()

    private fun runCycle(rocks: Map<Pair<Int,Int>, Char>): Map<Pair<Int,Int>, Char> =
        platforms.keys.fold(rocks) { acc, dir ->  run(acc, dir) }

    override fun part2() : Any {
        return (0..1_000_000_000).fold(rocks) { acc, i ->
            println(i)
//            printGrid(acc)

            if (acc in cache) {
                println("CACHE HIT ${cache[acc]} -> $i")

                return (1.. ((1_000_000_000 - cache[acc]!!) % (cache[acc]!! - i))).fold(acc) { acc2, j ->
                    println(j)
                    runCycle(acc2)
                }.let { getLoad(it, 'N') }
            }
            platforms.keys.fold(acc) { acc2, dir ->
                run(acc2, dir)
            }.also {
                cache[acc] = i
            }
        }.let { getLoad(it, 'N') }
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