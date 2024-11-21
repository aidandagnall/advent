package days

import util.InputReader
import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class Day(val day : Int) {

    protected val inputList: List<String> by lazy { InputReader.getInputAsList(day) }
    protected val inputIntList : List<Int> by lazy { inputList.map { it.toInt() }}
    protected val inputSplitList : List<List<String>> by lazy { inputList.map { it.split(' ')}}
    protected val inputGroupedList: List<List<String>> by lazy {
        var input = inputList
        val output = mutableListOf<List<String>>()
        while (input.isNotEmpty()) {
            val group = input.takeWhile { it != "" }
            output.add(group)
            input = input.drop(group.size + 1)
        }
        output.toList()
    }

    fun solve() {
        println("\tPart 1: ${part1()}")
        println("\tPart 2: ${part2()}")
    }

    @OptIn(ExperimentalTime::class)
    fun solveTimed() {
        val (part1, timeP1) = measureTimedValue { part1() }
        println("\tPart 1: $part1 in ${timeP1.toString(DurationUnit.SECONDS, 4)}")
        val (part2, timeP2) = measureTimedValue { part2() }
        println("\tPart 2: $part2 in ${timeP2.toString(DurationUnit.SECONDS, 4)}")
    }

    abstract fun part1() : Any

    abstract fun part2() : Any
}