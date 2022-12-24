package days

import util.*
import kotlin.math.min

class Day24 : Day(24) {
    private val height = inputList.drop(1).dropLast(1).size
    private val width = inputList[0].drop(1).dropLast(1).length
    private val start = 0 to -1
    private val end = width - 1 to height
    private val initial = inputList.drop(1).dropLast(1).mapIndexed { y, line ->
        line.drop(1).dropLast(1).mapIndexed { x, c ->
            if (c in listOf('<', '^', '>' , 'v')) (x to y) to c else null
        }
    }.flatten().filterNotNull()
    private val lcm = height.lcm(width)
    private val blizzards = List(lcm) { getBlizzards(it) }
    private val availableSpaces = List(lcm) {
        (0 until height).flatMap { y ->
            (0 until width).mapNotNull { x ->
                if (x to y !in blizzards[it]) x to y else null
            }
        } + start + end
    }.map { it.toSet() }

    override fun part1() : Any = search(State(start, 0), State(start, 0), end)

    override fun part2() : Any {
        return listOf(start to end, end to start, start to end).fold(0) { state, (start, end) ->
            search(State(start, state), State(start, state), end)
        }
    }

    data class State(val pos: Pair<Int,Int>, val minute: Int)

    private var currentBest = mutableMapOf<Pair<State, Pair<Int,Int>>, Int>()
    private val cache = mutableMapOf<Triple<Pair<Int,Int>, State, Pair<Int,Int>>, Int>()
    private fun search(state: State, start: State, target: Pair<Int,Int>,): Int {
        if (start == state) cache.clear()
        if (state.pos == target) {
            currentBest[start to target] = min(currentBest[start to target] ?: Int.MAX_VALUE, state.minute)
            return state.minute
        }
        if (state.minute > currentBest.getOrDefault(start to target, Int.MAX_VALUE)) return Int.MAX_VALUE
        if (Triple(start.pos, state, target) in cache) return cache[Triple(start.pos, state, target)]!!

        val moves = state.pos.neighbours().asSequence().map { State(it, state.minute + 1) }
            .filter { it.pos in availableSpaces[(state.minute + 1) % lcm] }
            .filter { !(it.pos == start.pos && state.pos != start.pos) }
            .filter { state.minute + it.pos.manhattan(target) < currentBest.getOrDefault(start to target, Int.MAX_VALUE) }
            .sortedBy { if (it.pos == start.pos) Int.MAX_VALUE else it.pos.manhattan(target) }.toList()

        val best = moves.minOfOrNull {
            if (Triple(start.pos, it, target) in cache) cache[Triple(start.pos, it, target)]!!
            else search(it, start, target)
        } ?: Int.MAX_VALUE

        currentBest[start to target] = min(currentBest.getOrDefault(start to target, Int.MAX_VALUE), best)
        cache[Triple(start.pos, state, target)] = best
        return best
    }

    private fun getBlizzards(minute: Int) : Set<Pair<Int,Int>> {
        return initial.map { (pos, dir) ->
            (pos + (when (dir) {
                '<' -> -1 to 0
                '^' -> 0 to -1
                '>' -> 1 to 0
                'v' -> 0 to 1
                else -> throw Exception("Invalid direction $dir")
            } * minute)).let { (a, b) -> a.positiveMod(width) to b.positiveMod(height)}
        }.toSet()
    }

    private fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> {
        return listOf(
            first to second,
            first - 1 to second,
            first + 1 to second,
            first to second - 1,
            first to second + 1,
        )
    }
}