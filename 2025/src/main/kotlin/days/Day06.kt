package days

import util.product
import util.split

class Day06 : Day(6) {
    val numbers = inputList.dropLast(1).map { it.split(" ").filter { it.isNotEmpty() }.map { it.toLong() } }
    val operations = inputList.last().split(" ").filter { it.isNotEmpty() }
    override fun part1() : Any = numbers.first().indices.sumOf { index ->
            numbers.map { it[index] }.doOp(operations[index])
        }

    override fun part2() : Any {
        val columns = (0..< inputList.maxOf { it.length }).reversed().map { col ->
            inputList.dropLast(1).joinToString("") { it.getOrElse(col) { ' ' }.toString().trim() }
        }

        return columns.split { it.isBlank() }
            .mapIndexed { index, values ->
                values.map { it.toLong() }.doOp(operations.reversed()[index])
            }.sum()
    }

    fun List<Long>.doOp(op: String) = when (op) {
        "+" -> sum()
        "*" -> product()
        else -> error("Unknown op")
    }
}