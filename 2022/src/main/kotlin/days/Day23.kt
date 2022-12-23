package days

import util.plus

class Day23 : Day(23) {

    private val initial = inputList.flatMapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if (c == '#') x to y else null
        }
    }.filterNotNull().toSet()

    // directions to check (if clear, propose the middle direction)
    private val directions = listOf(
        listOf(-1 to -1, 0 to -1, 1 to -1),    // NW, N, NE
        listOf(-1 to 1, 0 to 1 , 1 to 1),      // SW, S, SE
        listOf(-1 to -1, -1 to 0, -1 to 1),    // NW, W, SW
        listOf(1 to -1, 1 to 0, 1 to 1),       // NE, E, SE
    )

    override fun part1() : Any {
        return (0 until 10).fold(initial) { acc, i -> acc.doRound(i) }.let { result ->
            (result.maxOf { it.first } - result.minOf { it.first } + 1) * (result.maxOf { it.second } - result.minOf { it.second } + 1) - result.size
        }
    }

    override fun part2() : Any {
        generateSequence(0) { it + 1 }.fold(initial) { acc, i ->
            acc.doRound(i).let { if (it == acc) return i + 1 else it }
        }
        return -1
    }

    private fun Pair<Int,Int>.neighbours() : List<Pair<Int,Int>> {
        return (-1..1).flatMap { dx ->
            (-1..1).map { dy ->
                first + dx to second + dy
            }
        }.filter { it != first to second }
    }

    private fun Set<Pair<Int,Int>>.doRound(round: Int): Set<Pair<Int,Int>> {
        val proposed = mutableMapOf<Pair<Int,Int>, Int>()

        val order = directions.drop(round % 4) + directions.take(round % 4)
        val elves = this.map next@{ elf ->
            if (elf.neighbours().all { it !in this}) {
                return@next elf to null
            }
            order.forEach { direction ->
                if (direction.all { elf + it !in this}) {
                    proposed[elf + direction[1]] = proposed.getOrDefault(elf + direction[1], 0) + 1
                    return@next elf to elf + direction[1]
                }
            }
            elf to null
        }

        return elves.map { (pos, dest) ->
            if (proposed[dest] == 1) dest!!
            else pos
        }.toSet()
    }
}