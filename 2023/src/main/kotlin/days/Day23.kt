package days

import util.neighbours4
import util.plus

class Day23 : Day(23) {
    val map = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            (x to y) to c
        }
    }.toMap()

    private val start = inputList.first().indexOfFirst { it == '.' } to 0
    private val end = inputList.last().indexOfFirst { it == '.' } to inputList.count() - 1

    override fun part1(): Any = search(start, end, true).first - 1
    override fun part2(): Any = search(start, end, false).first - 1

    private fun search(
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        icy: Boolean = true,
        seenBefore: Set<Pair<Int, Int>> = emptySet()
    ): Pair<Int, List<Pair<Int, Int>>> {
        val queue = mutableListOf(start)
        val seen = seenBefore.toMutableSet()
        val path = mutableListOf<Pair<Int, Int>>()

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            path.add(current)

            if (current == end) {
                break
            }
            seen.add(current)

            val next = when (icy) {
                true -> when (map[current]) {
                    '.' -> current.neighbours4()
                        .asSequence()
                        .filter { it in map && map[it] != '#' }
                        .filterNot { it == current + (-1 to 0) && map[it] == '>' }
                        .filterNot { it == current + (1 to 0) && map[it] == '<' }
                        .filterNot { it == current + (0 to 1) && map[it] == '^' }
                        .filterNot { it == current + (0 to -1) && map[it] == 'v' }
                        .filter { it !in seen }
                        .toList()

                    '>' -> listOf(current + (1 to 0))
                    '<' -> listOf(current + (-1 to 0))
                    'v' -> listOf(current + (0 to 1))
                    '^' -> listOf(current + (0 to -1))
                    else -> error("Invalid character")
                }

                false -> current.neighbours4()
                    .filter { it in map && map[it] != '#' }
                    .filter { it !in seen }
            }

            when (next.size) {
                0 -> return 0 to emptyList()
                1 -> queue.addAll(next)
                else -> {
                    val options = next.map {
                        it to search(it, end, icy, seen)
                    }
                    val (_, result) = options.maxBy { it.second.first }
                    return result
                }
            }

        }
        return path.size + seenBefore.size to path
    }
}