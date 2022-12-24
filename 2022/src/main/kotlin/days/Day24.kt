package days

import util.manhattan
import util.plus
import kotlin.math.min

class Day24 : Day(24) {
    private val height = inputList.size - 2
    private val width = inputList[0].length - 2
    private val start = 0 to -1
    private val end = width - 1 to height
    private val blizzards = inputList.drop(1).dropLast(1).mapIndexed { y, line ->
        line.drop(1).dropLast(1).mapIndexed { x, c ->
            if (c in listOf('<', '^', '>' , 'v')) (x to y) to c else null
        }
    }.flatten().filterNotNull()

    override fun part1() : Any {
        return search(State(start, 0), start, end)
    }

    override fun part2() : Any {
        val first = search(State(start, 0), start, end)
        currentBest = Int.MAX_VALUE
        cache.clear()
        val second = search(State(end, first), end, start)

        currentBest = Int.MAX_VALUE
        cache.clear()
        return search(State(start, second), start, end)
    }

    data class State(val pos: Pair<Int,Int>, val minute: Int)

    private var currentBest = Int.MAX_VALUE
    private val cache = mutableMapOf<State, Int>()
    private fun search(state: State, start: Pair<Int,Int>, target: Pair<Int,Int>, left: Boolean = false): Int {
        if (state.pos == target) return state.minute
        if (state.minute > currentBest) return Int.MAX_VALUE

        val newBlizzards = getBlizzards(state.minute + 1)
        val moves = ((state.pos.neighbours() + state.pos).map { State(it, state.minute + 1) })
            .filter { it.pos !in newBlizzards.map { it.first } }
            .filter { state.minute + it.pos.manhattan(target) < currentBest }
            .sortedBy { if (it.pos == start) Int.MAX_VALUE else it.pos.manhattan(target) }

        val best = moves.minOfOrNull {
            if (it.pos == start && left) Int.MAX_VALUE
            else if (it in cache) cache[it]!!
            else search(it, start, target, it.pos != start || left)
        } ?: Int.MAX_VALUE
        currentBest = min(currentBest, best)
        cache[state] = best
        return best
    }

    private fun getBlizzards(minute: Int) : List<Pair<Pair<Int,Int>, Char>> {
        return blizzards.map { (pos, dir) ->
            (pos + (when (dir) {
                '<' -> -1 to 0
                '^' -> 0 to -1
                '>' -> 1 to 0
                'v' -> 0 to 1
                else -> throw Exception("Invalid direction $dir")
            } * minute)).let { (a, b) -> a.positiveMod(width) to b.positiveMod(height)} to dir
        }
    }

    private operator fun Pair<Int,Int>.times(other: Int) = first * other to second * other

    private fun Int.positiveMod(other: Int) = if (this % other < 0) other + (this % other) else this % other

    private fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> {
        return listOf(
            this.first - 1 to this.second,
            this.first + 1 to this.second,
            this.first to this.second - 1,
            this.first to this.second + 1,
        ).filter{ (x, y) -> y in 0 until height || x to y == end || x to y == start }.mapNotNull { (x, y) -> if ((x in 0 until width && y in 0 until height) || x to y == end || x to y == start) x to y else null }
    }
}