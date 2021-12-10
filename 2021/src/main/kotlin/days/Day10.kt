package days

import java.util.*

class Day10 : Day(10) {
    enum class State { VALID, INCOMPLETE, CORRUPTED }
    private val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    private val values = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4,
        ')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    override fun part1() : Any {
        return inputList.map { it.validateSyntax() }
            .filter { it.first == State.CORRUPTED }
            .sumOf { values[it.second] as Int }
    }

    override fun part2() : Any {
        val values = inputList.map { it.validateSyntax() }
            .filter { it.first == State.INCOMPLETE }
            .map { it.second as Long}
            .sorted()
        return values[values.size / 2]
    }

    private fun String.validateSyntax() : Pair<State, Any> {
        val stack = Stack<Char>()
        this.forEach {
            when (it) {
                in pairs.keys -> stack.push(it)             // if char in keys, then it is opening, so add to stack
                pairs[stack.peek()] -> stack.removeLast()   // if it is *valid* close, remove opening from stack
                else -> return State.CORRUPTED to it        // if it is invalid close, return char it failed on
            }
        }
        return if (stack.isEmpty()) {
            State.VALID to 0
        } else {
            State.INCOMPLETE to stack.map { values[it] as Int }
                .reversed()
                .fold(0L) { acc, next -> acc * 5 + next }
        }
    }
}