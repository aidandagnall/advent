package days

import util.neighbours8
import util.parseGrid

class Day04 : Day(4) {
    val positions = inputList.parseGrid { pos, ch -> if (ch == '@') pos else null }.toSet()
    override fun part1() : Any = positions.count { positions.canAccess(it) }

    override fun part2() : Any = generateSequence(positions) { prev ->
        prev.removeAccessible().let { if (it == prev) null else it }
    }.last().let { positions - it }.count()

    fun Set<Pair<Int,Int>>.removeAccessible(): Set<Pair<Int,Int>> = this - filter { canAccess(it) }.toSet()
    fun Set<Pair<Int,Int>>.canAccess(pos: Pair<Int,Int>): Boolean = pos.neighbours8().count { it in this } < 4
}