package days

import util.InputReader

abstract class Day(private val day : Int) {

    protected val inputList: List<String> by lazy { InputReader.getInputAsList(day) }
    protected val inputIntList : List<Int> by lazy { inputList.map { it.toInt() }}

    fun solve() {
        println("Day $day:")
        println("\tPart 1: ${part1()}")
        println("\tPart 2: ${part2()}")
    }

    abstract fun part1() : Any

    abstract fun part2() : Any
}