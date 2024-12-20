package days

import util.manhattan
import util.neighbours4
import util.parseGrid

class Day20 : Day(20) {
    private val walls = inputList.parseGrid { pos, c -> if (c == '#') pos else null }.toSet()
    private val start = inputList.parseGrid { pos, c -> if (c == 'S') pos else null }.first()
    private val end = inputList.parseGrid { pos, c -> if (c == 'E') pos else null}.first()
    private val xRange = walls.minOf { it.first }..walls.maxOf { it.first }
    private val yRange = walls.minOf { it.second }..walls.maxOf { it.second }

    data class State(val pos: Pair<Int,Int>, val step: Int)

    private fun State.getNext(): List<State> =
        pos.neighbours4()
            .filter { it.first in xRange && it.second in yRange }
            .filter { it !in walls }
            .map { copy(pos = it, step + 1) }

    private fun search(): List<State> {
        val start = State(start, 0)
        val queue = mutableListOf(start)
        val seen = mutableSetOf(start.pos)
        val out = mutableListOf<State>()

        while(queue.isNotEmpty()) {
            val current = queue.removeFirst()
            out.add(current)

            if (current.pos == end) {
                return out
            }

            current.getNext()
                .filter { it.pos !in walls && it.pos !in seen }
                .forEach {
                    queue.add(it)
                    seen.add(it.pos)
                }
        }
        return emptyList()
    }

    override fun part1() : Any = getSavingCheats(2, 100)
    override fun part2() : Any = getSavingCheats(20, 100)

    fun getSavingCheats(cheat: Int, saving: Int): Int {
        val states = search()
        val end = states.last()
        val candidates = mutableListOf<Pair<State, State>>()

        states.forEach { a ->
            states.forEach inner@{ b ->
                if (a == b) return@inner
                if (a.step > b.step) return@inner
                if (a.pos.manhattan(b.pos) > cheat) return@inner
                if (a.step + (a.pos.manhattan(b.pos)) + (end.step - b.step) > (end.step - saving)) return@inner
                candidates.add(a to b)
            }
        }

        return candidates.count()
    }

}