package days

import kotlin.math.pow

class Day17 : Day(17) {
    private val registers = inputList.take(3).map { it.substringAfter(": ").toLong() }
    private val instructions = inputList.drop(4).first().substringAfter(": ").split(",").map { it.toInt() }

    override fun part1() : Any = run(registers[0], registers[1], registers[2]).joinToString(",")
    override fun part2(): Any = search("", emptyList()) ?: 0

    fun run(initA: Long, initB: Long, initC: Long, ins: List<Int> = instructions): List<Int> {
        var pointer = -2
        var a = initA
        var b = initB
        var c = initC

        val out = mutableListOf<Long>()
        while(true) {
            pointer += 2
            if (pointer !in ins.indices) break

            val literalOperand = ins[pointer + 1].toLong()
            val comboOperand = when (ins.getOrNull(pointer + 1)) {
                    in 0..3 -> ins[pointer + 1].toLong()
                    4 -> a
                    5 -> b
                    6 -> c
                    else -> error("")
                }

            when (ins.getOrNull(pointer)) {
                0 -> a = (a / 2.0.pow(comboOperand.toInt())).toLong()
                1 -> b = b xor literalOperand
                2 -> b = comboOperand % 8
                3 -> if (a != 0L) pointer = literalOperand.toInt() - 2
                4 -> b = b xor c
                5 -> out.add(comboOperand % 8)
                6 -> b = (a / 2.0.pow(comboOperand.toInt())).toLong()
                7 -> c = (a / 2.0.pow(comboOperand.toInt())).toLong()
                else -> error("")
            }
        }

        return out.map { it.toInt() }
    }

    private fun search(a: String, result: List<Int>): Long? {
        if (result == instructions) return a.toLong(2)
        if (a.length > (result.size + 3) * 3) return null                    // rough heuristic to prevent stack overflow for starting with 0
        if (result != instructions.takeLast(result.size)) return null
        return (0..7).map { a + it.toString(2).padStart(3, '0') }
            .map { it to run(it.toLong(2), 0, 0) }
            .firstNotNullOfOrNull { (i, res) -> search(i, res) }
    }
}