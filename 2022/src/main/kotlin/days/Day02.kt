package days

class Day02 : Day(2) {

    // Rock     -> 0,  Loss -> 0
    // Paper    -> 1,  Draw -> 1
    // Scissors -> 2,  Win  -> 2
    private val input = inputList
        .map { it.split(" ") }
        .map { (a, b) -> a.first() - 'A' to b.first() - 'X' }

    override fun part1() : Any {
        return input.sumOf { (a, b) -> b + 1 + 3 * compareHands(b, a) }
    }

    override fun part2() : Any {
        return input.sumOf { (a, b) -> 3 * b + getResult(a, b) + 1}
    }

    private fun compareHands(a: Int, b: Int): Int = (4 + a - b) % 3
    private fun getResult(shape: Int, result: Int): Int = when (result) {
            0 -> (3 + shape - 1) % 3
            1 -> (shape)
            2 -> (shape + 1) % 3
            else -> 0
        }
}