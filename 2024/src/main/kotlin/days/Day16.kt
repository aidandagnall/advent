package days

import util.Direction
import util.parseGrid
import util.plus
import java.util.PriorityQueue

class Day16 : Day(16) {
    data class State(val pos: Pair<Int,Int>, val dir: Direction, val score: Int, val history: List<Pair<Int,Int>> = emptyList())

    private val walls = inputList.parseGrid { pos, c -> if (c == '#') pos else null }.toSet()

    private val start = inputList.parseGrid { pos, c -> if (c == 'S') pos else null }.first()
    private val end = inputList.parseGrid { pos, c -> if (c == 'E') pos else null }.first()

    override fun part1() = search(start, end).first().score
    override fun part2() = search(start, end).flatMap { it.history }.distinct().count() + 1

    private fun search(start: Pair<Int,Int>, end: Pair<Int,Int>): List<State> {
        val queue: PriorityQueue<State> = PriorityQueue(1000, Comparator.comparingInt{ it.score })
        val seen = mutableMapOf<Pair<Pair<Int,Int>,Direction>,Int>()
        queue.add(State(start, Direction.EAST, 0, emptyList()))

        val paths: MutableList<State> = mutableListOf()

        while(queue.isNotEmpty()) {
            val current = queue.poll()
            val (pos, dir, score, history) = current
            if (pos to dir in seen.keys && seen[pos to dir]!! < score) continue

            if (pos == end) {
                if (score > (paths.minOfOrNull { it.score } ?: Int.MAX_VALUE)) break
                paths.add(current)
                continue
            }

            seen[(pos to dir)] = score
            listOf(
                current.copy(pos = pos + dir, score = score + 1, history = history + pos),
                current.copy(dir = dir.turnRight(), score = score + 1000),
                current.copy(dir = dir.turnLeft(), score = score + 1000),
            ).filter { it.pos !in walls }
            .forEach { queue.add(it) }
        }

        return paths.filter { it.score == paths.minOf { it.score } }
    }
}