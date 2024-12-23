package days

import kotlin.math.pow

class Day17 : Day(17) {
    private val registers = inputList.take(3).map { it.substringAfter(": ").toLong() }
    private val instructions = inputList.drop(4).first().substringAfter(": ").split(",").map { it.toInt() }

    private val initialState = State(registers[0], registers[1], registers[2], ins = instructions)

    override fun part1() : Any = initialState.run().out.joinToString(",")
    override fun part2(): Any = search("", emptyList()) ?: 0

    data class State(val a: Long, val b: Long, val c: Long, val ins: List<Int>, val out: List<Int> = emptyList(), val pointer: Int = 0) {
        val opcode = ins.getOrNull(pointer)
        val literalOperand by lazy { ins[pointer + 1] }
        val comboOperand by lazy {
            when (ins.getOrNull(pointer + 1)) {
                in 0..3 -> ins[pointer + 1].toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> error("")
            }
        }
    }

    tailrec fun State.run(): State {
        return when(opcode) {
            0 -> copy(a = a / 2.0.pow(comboOperand.toInt()).toLong(), pointer = pointer + 2)
            1 -> copy(b = b xor literalOperand.toLong(), pointer = pointer + 2)
            2 -> copy(b = comboOperand % 8, pointer = pointer + 2)
            3 -> if (a == 0L) copy(pointer = pointer + 2) else copy(pointer = literalOperand)
            4 -> copy(b = b xor c, pointer = pointer + 2)
            5 -> copy(out = out + (comboOperand % 8).toInt(), pointer = pointer + 2)
            6 -> copy(b = a / 2.0.pow(comboOperand.toInt()).toLong(), pointer = pointer + 2)
            7 -> copy(c = a / 2.0.pow(comboOperand.toInt()).toLong(), pointer = pointer + 2)
            null -> return this
            else -> error("")
        }.run()
    }

    private fun search(a: String, result: List<Int>): Long? {
        if (result == instructions) return a.toLong(2)
        if (a.length > (result.size + 3) * 3) return null                    // rough heuristic to prevent stack overflow for starting with 0
        if (result != instructions.takeLast(result.size)) return null
        return (0..7).map { a + it.toString(2).padStart(3, '0') }
            .map { it to initialState.copy(a = it.toLong(2)).run() }
            .firstNotNullOfOrNull { (i, res) -> search(i, res.out) }
    }
}