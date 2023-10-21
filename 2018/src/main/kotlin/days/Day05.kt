package days

class Day05 : Day(5) {

    override fun part1() : Any = react(inputList[0])

    override fun part2() : Any = ('a'..'z').minOf { c ->
            react(inputList[0].filter { it != c && it != c.uppercaseChar() })
    }

    private fun react(polymer: String): Int {
        val stack = mutableListOf<Char>()
        polymer.forEach {
            when {
                stack.isEmpty() -> stack.add(it)
                it.reactsWith(stack.last()) -> stack.removeLast()
                else -> stack.add(it)
            }
        }
        return stack.size
    }

    private fun Char.reactsWith(other: Char) : Boolean {
        return this != other && this.uppercaseChar() == other.uppercaseChar()
    }
}
