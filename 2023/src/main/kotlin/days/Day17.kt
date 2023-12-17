package days

import util.manhattan
import java.util.Comparator
import java.util.PriorityQueue

// 923 too high

class Day17 : Day(17) {

    val nodes = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            (x to y) to c.digitToInt()
        }
    }.toMap()

    val start = 0 to 0
    val end = inputList.first().length - 1 to inputList.size - 1

    private fun nextCrucible(current: State, history: List<State>): List<State> {
        return when (current.dir) {
            'U' -> when(current.dirCount) {
                in 0..3 -> listOf(
                    State(current.x, current.y - 1, current.dir, current.dirCount + 1)
                )
                in 4..9 -> listOf(
                    State(current.x, current.y - 1, current.dir, current.dirCount + 1),
                    State(current.x + 1, current.y, 'R', 1),
                    State(current.x - 1, current.y, 'L', 1)
                )
                else -> listOf(
                    State(current.x + 1, current.y, 'R', 1),
                    State(current.x - 1, current.y, 'L', 1)
                )
            }
            'D' -> when(current.dirCount) {
                in 0..3 -> listOf(
                    State(current.x, current.y + 1, current.dir, current.dirCount + 1)
                )
                in 4..9 -> listOf(
                    State(current.x, current.y + 1, current.dir, current.dirCount + 1),
                    State(current.x + 1, current.y, 'R', 1),
                    State(current.x - 1, current.y, 'L', 1)
                )
                else -> listOf(
                    State(current.x + 1, current.y, 'R', 1),
                    State(current.x - 1, current.y, 'L', 1)
                )
            }
            'L' -> when(current.dirCount) {
                in 0..3 -> listOf(
                    State(current.x - 1, current.y, current.dir, current.dirCount + 1)
                )
                in 4..9 -> listOf(
                    State(current.x - 1, current.y, current.dir, current.dirCount + 1),
                    State(current.x, current.y + 1, 'D', 1),
                    State(current.x, current.y - 1, 'U', 1)
                )
                else -> listOf(
                    State(current.x, current.y + 1, 'D', 1),
                    State(current.x, current.y - 1, 'U', 1)
                )
            }
            else -> when(current.dirCount) {
                in 0..3 -> listOf(
                    State(current.x + 1, current.y, current.dir, current.dirCount + 1)
                )
                in 4..9 -> listOf(
                    State(current.x + 1, current.y, current.dir, current.dirCount + 1),
                    State(current.x, current.y + 1, 'D', 1),
                    State(current.x, current.y - 1, 'U', 1)
                )
                else -> listOf(
                    State(current.x, current.y + 1, 'D', 1),
                    State(current.x, current.y - 1, 'U', 1)
                )
            }
        }
    }

    private fun next(current: State, history: List<State>): List<State> {
        return when (current.dir) {
            'U' -> if (history.takeLast(3).all { it.dir == 'U' }) {
                emptyList()
            } else  {
                listOf(State(current.x, current.y - 1, current.dir, current.dirCount + 1))
            } +
                listOf(
                    State(current.x + 1, current.y, 'R', 0),
                    State(current.x - 1, current.y, 'L', 0)
                )
            'D' -> if (history.takeLast(3).all { it.dir == 'D' }) {
                emptyList()
            } else  {
                listOf(State(current.x, current.y + 1, current.dir, current.dirCount + 1))
            } +
                    listOf(
                        State(current.x + 1, current.y, 'R', 0),
                        State(current.x - 1, current.y, 'L', 0)
                    )
            'L' -> if (history.takeLast(3).all { it.dir == 'L' }) {
                emptyList()
            } else  {
                listOf(State(current.x - 1, current.y, current.dir, current.dirCount + 1))
            } +
                    listOf(
                        State(current.x, current.y + 1, 'D', 0),
                        State(current.x, current.y- 1, 'U', 0)
                    )
            else -> if (history.takeLast(3).all { it.dir == 'R' }) {
                emptyList()
            } else  {
                listOf(State(current.x + 1, current.y , current.dir, current.dirCount + 1))
            } +
                    listOf(
                        State(current.x, current.y+ 1, 'D', 0),
                        State(current.x, current.y - 1, 'U', 0)
                    )
        }
    }

    private fun path(from: Map<State,State>, dest: State): List<State> {
//        println("Getting path")
        var current = dest
        val total = mutableListOf(dest)
//        println(from)
        while(current in from) {
            current = from[current]!!
            total.add(0, current)
        }
//        println("Done getting path")
        return total.toList()
    }

    data class State(val x: Int, val y: Int, val dir: Char, val dirCount: Int)
    private fun astar(start: Pair<Int,Int>, end: Pair<Int,Int>): Int {
        val cameFrom = mutableMapOf<State, State>()

        val gScores = mutableMapOf(
            State(start.first, start.second, 'U', 0) to 0,
            State(start.first, start.second, 'D', 0) to 0,
            State(start.first, start.second, 'L', 0) to 0,
            State(start.first, start.second, 'R', 0) to 0,
        )

        val openSet: PriorityQueue<State> = PriorityQueue(1000, Comparator.comparingInt{ gScores[it] ?: 100000 })
        openSet.add(State(start.first, start.second, 'R', 0))

        val openSetSet = mutableSetOf<State>()
        openSetSet.add(State(start.first, start.second, 'R', 0))

        while(openSet.isNotEmpty()) {
//            println(openSet)
            val current = openSet.poll()
            openSetSet.remove(current)

            if (current.x to current.y == end) {
                val path = path(cameFrom, current)
                println()
                println(path.map { it to gScores[it] })

                println(gScores)

                println()

                println((start.second..end.second).joinToString("\n") { y ->
                    (start.first..end.first).joinToString("") { x ->
                        if (x to y in path.map { it.x to it.y }) path.find { it.x == x && it.y == y }!!.dir.let {
                            when(it) {
                                'U' -> '^'
                                'D' -> 'v'
                                'L' -> '<'
                                else -> '>'
                            }
                        }.toString() else nodes[x to y].toString()
                    }
                })
                println(nodes[end])
                return gScores[current]!!
            }

            nextCrucible(current, path(cameFrom, current))
                .filter { (x, y, _) -> x in 0..end.first && y in 0..end.second }
                .forEach {

                val tent = gScores.getOrDefault(current, 100000) + nodes[it.x to it.y]!!
                if (tent < gScores.getOrDefault(it, 100000)) {

                    if (it.x to it.y == end && it.dirCount < 4) {
                        return@forEach
                    }
                        cameFrom[it] = current
                        gScores[it] = tent

                        if (it !in openSetSet) {
                            openSet.add(it)
                            openSetSet.add(it)
                        }
                }

            }

        }
        return -1
    }

    override fun part1() : Any {
        return astar(start, end)
    }

    override fun part2() : Any {
        return astar(start, end)
    }
}