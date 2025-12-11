package days

import com.microsoft.z3.Context
import com.microsoft.z3.IntNum

class Day10 : Day(10) {

    data class Machine(
        val neededState: List<Boolean>,
        val buttons: List<List<Int>>,
        val joltage: List<Int>,
    ) {
        fun findNeededPresses(): Int {
            val paths = mutableMapOf(neededState.map { false } to 0)
            val toCheck = mutableListOf(neededState.map { false })

            while(toCheck.isNotEmpty()) {
                val next = toCheck.removeFirst()
                val possibleStates = buttons.map { next.mapIndexed { index, i -> if (index in it) !i else i } }

                toCheck.addAll(possibleStates.filter { it !in paths})

                possibleStates.filter { it !in paths }.forEach {
                    if (it == neededState) return paths[next]!! + 1
                    paths[it] = paths[next]!! + 1
                }
            }
            return 0
        }

        fun findNeededPressesJoltage(): Int  = with(Context()){
            val opt = mkOptimize()

            val bs = buttons.indices.map { mkIntConst("press$it") }.toTypedArray()
            val totalPresses = mkIntConst("presses")

            opt.MkMinimize(mkAdd(*bs))
            opt.Add(mkAnd(*bs.map { mkGe(it, mkInt(0)) }.toTypedArray()))

            joltage.forEachIndexed  { index, target ->
                val possibleButtons = buttons.withIndex().filter { index in it.value}.map { bs[it.index] }
                opt.Add(mkEq(mkInt(target), mkAdd(*possibleButtons.toTypedArray())))
            }
            opt.Add(mkEq(totalPresses, mkAdd(*bs)))

            opt.Check()
            return opt.model.eval(totalPresses, false).let { it as IntNum }.int
        }
    }


    val machines = inputList.map {
        val sections = it.split(" ")
        val state = sections.first().drop(1).dropLast(1).map { it == '#' }
        val joltages = sections.last().drop(1).dropLast(1).split(",").map { it.toInt() }
        val buttons = sections.drop(1).dropLast(1).map { it.drop(1).dropLast(1).split(",").map { it.toInt() } }
        Machine(state, buttons, joltages)
    }

    override fun part1() : Any = machines.sumOf { it.findNeededPresses() }
    override fun part2() : Any = machines.sumOf { it.findNeededPressesJoltage() }
}