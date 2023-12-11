package days

import util.plus

class Day10 : Day(10) {

    private val coords = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if (c == '.') null else (x to y) to c
        }.filterNotNull()
    }.toMap()
    private val start = coords.filterValues { it == 'S' }.toList().first().first

    private fun getNext(current: Pair<Int, Int>): List<Pair<Int, Int>> {
        return when (coords[current]) {
            '|' -> listOf(current + (0 to -1), current + (0 to 1))
            '-' -> listOf(current + (-1 to 0), current + (1 to 0))
            'L' -> listOf(current + (0 to -1), current + (1 to 0))
            'J' -> listOf(current + (0 to -1), current + (-1 to 0))
            '7' -> listOf(current + (-1 to 0), current + (0 to 1))
            'F' -> listOf(current + (0 to 1), current + (1 to 0))
            'S' -> listOf(
                current + (-1 to 0),
                current + (0 to -1),
                current + (0 to 1),
                current + (1 to 0),
            ).filter { current in getNext(it) }
            else -> emptyList()
        }
    }

    override fun part1(): Any {
        return bfs().let { connections ->
            connections.filterKeys { it == start }.toList().first().let { coord ->
                generateSequence(coord.first) {
                    connections[it]
                }.count() / 2
            }
        }
    }

    override fun part2(): Any {
        val path = bfs().let { connections ->
            connections.filterKeys { it == start }.toList().first().let { coord ->
                generateSequence(coord.first) {
                    connections[it]
                } + coord.first
            }.windowed(2).toList()
        }

        return listOf(
            (start.first + 0.5).toFloat() to (start.second + 0.5).toFloat(),
            (start.first - 0.5).toFloat() to (start.second - 0.5).toFloat(),
        ).maxOf {
            fill(it, path.associate { (a, b) -> a to b })
        }
    }

    private val minX = coords.keys.minOf { it.first }
    private val maxX = coords.keys.maxOf { it.first }
    private val minY = coords.keys.minOf { it.second }
    private val maxY = coords.keys.maxOf { it.second }

    private fun bfs(): Map<Pair<Int, Int>, Pair<Int, Int>> {
        val (first, end) = getNext(start)
        val queue = mutableListOf(first)
        val explored = mutableSetOf(start, first)
        val children = mutableMapOf(start to first)

        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            if (v == end) {
                return children.toMap()
            }
            getNext(v).forEach {
                if (it !in explored) {
                    explored.add(it)
                    children[v] = it
                    queue.add(it)
                }
            }
        }
        return children
    }

    private fun fill(start: Pair<Float,Float>, path: Map<Pair<Int,Int>,Pair<Int,Int>>): Int {
        val done = mutableSetOf(start)
        val queue = mutableListOf(start)
        val counts = mutableSetOf<Pair<Int,Int>>()

        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            if (v.first < minX || v.first > maxX || v.second < minY || v.second > maxY) {
                return 0
            }
            val next = listOf(
                v.first + 1 to v.second,
                v.first - 1 to v.second,
                v.first to v.second + 1,
                v.first to v.second - 1,
            )
            next.forEachIndexed { i, n ->
                if (n in done) {
                    return@forEachIndexed
                }

                val (a,b) = when(i) {
                    0 -> listOf(
                        (v.first + 0.5).toInt() to (v.second + 0.5).toInt(),
                        (v.first + 0.5).toInt() to (v.second - 0.5).toInt(),
                    )
                    1 -> listOf(
                        (v.first - 0.5).toInt() to (v.second + 0.5).toInt(),
                        (v.first - 0.5).toInt() to (v.second - 0.5).toInt(),
                    )
                    2 -> listOf(
                        (v.first + 0.5).toInt() to (v.second + 0.5).toInt(),
                        (v.first - 0.5).toInt() to (v.second + 0.5).toInt(),
                    )
                    3 -> listOf(
                        (v.first + 0.5).toInt() to (v.second - 0.5).toInt(),
                        (v.first - 0.5).toInt() to (v.second - 0.5).toInt(),
                    )
                    else -> {
                        listOf()
                    }

                }

                if (path[a] != b && path[b] != a) {
                    done.add(n)
                    queue.add(n)

                    if (a !in path.keys && a !in path.values) {
                        counts.add(a)
                    }
                    if (b !in path.keys && b !in path.values) {
                        counts.add(b)
                    }
                }
            }
        }

        return counts.count()
    }


}