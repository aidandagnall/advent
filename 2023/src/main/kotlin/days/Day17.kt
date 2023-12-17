package days

import util.plus
import java.util.Comparator
import java.util.PriorityQueue

class Day17 : Day(17) {
    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        fun opposites(): List<Direction> = when(this) {
            UP, DOWN -> listOf(LEFT, RIGHT)
            LEFT, RIGHT -> listOf(UP, DOWN)
        }

        fun toVector(): Pair<Int,Int> = when(this) {
            UP -> 0 to -1
            DOWN -> 0 to 1
            LEFT -> -1 to 0
            RIGHT -> 1 to 0
        }
    }
    data class State(val coord: Pair<Int,Int>, val dir: Direction, val dirCount: Int) {
        fun forward(): List<State> = listOf(copy(coord = coord + dir.toVector(), dirCount = dirCount + 1))

        fun turns(): List<State> = dir.opposites().map {
            State(coord + it.toVector(), it, 1)
        }
    }

    private val nodes = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            (x to y) to c.digitToInt()
        }
    }.toMap()

    private val start = 0 to 0
    private val end = inputList.first().length - 1 to inputList.size - 1

    private fun crucibleNeighbours(current: State): List<State> =
        current.turns() + if (current.dirCount <= 2)  current.forward() else emptyList()

    private fun ultraCrucibleNeighbours(current: State): List<State> = when(current.dirCount) {
        in 0..3 -> current.forward()
        in 4..9 -> current.turns() + current.forward()
        else -> current.turns()
    }

    private fun search(start: Pair<Int,Int>, end: Pair<Int,Int>, next: (State) -> List<State>, runOff: Int = 0): Int {
        val scores = Direction.entries.associate {
            State(start, it, 0) to 0
        }.toMutableMap()

        val queue: PriorityQueue<State> = PriorityQueue(1000, Comparator.comparingInt{ scores[it] ?: 100000 })
        val seen = mutableSetOf(
            State(start, Direction.RIGHT, 0),
            State(start, Direction.DOWN, 0)
        )
        queue.addAll(seen)

        while(queue.isNotEmpty()) {
            val current = queue.poll()

            if (current.coord == end) return scores[current]!!

            next(current)
                .filter { it.coord.first in 0..end.first && it.coord.second in 0..end.second }
                .filterNot { it.coord == end && it.dirCount < runOff }
                .forEach {
                    val tent = scores.getOrDefault(current, 100000) + nodes[it.coord]!!
                    if (tent < scores.getOrDefault(it, 100000)) {
                        scores[it] = tent
                        if (it !in seen) {
                            queue.add(it)
                            seen.add(it)
                        }
                    }
            }
        }
        return -1
    }

    override fun part1() : Any = search(start, end, ::crucibleNeighbours)
    override fun part2() : Any = search(start, end, ::ultraCrucibleNeighbours, 4)
}