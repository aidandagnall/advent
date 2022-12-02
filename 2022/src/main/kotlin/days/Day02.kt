package days

class Day02 : Day(2) {

    private val input = inputList
        .map { it.split(" ") }
        .map { (a, b) -> HandShape((a.first() - 'A')) to b.first() - 'X' }

    private val resultScore = mapOf (-1 to 0, 0 to 3, 1 to 6)

    override fun part1() : Any {
        return input.sumOf { (a, b) ->
            HandShape(b).let {
                it.score + resultScore[it.compareTo(a)]!!
            }
        }
    }

    override fun part2() : Any {
        return input.sumOf { (a, b) -> resultScore[b - 1]!! + a.getResult(b).score }
    }

    data class HandShape(val shape: Int): Comparable<HandShape> {
        val score = shape + 1
        private val char = when (shape) {
            0 -> 'R'
            1 -> 'P'
            2 -> 'S'
            else -> 'E'
        }

        override fun compareTo(other: HandShape): Int {
            return when (this.char to other.char) {
                'R' to 'S', 'S' to 'P', 'P' to 'R' -> 1
                'R' to 'P', 'P' to 'S', 'S' to 'R' -> -1
                else -> 0
            }
        }

        fun getResult(result: Int): HandShape {
            return HandShape(when (result) {
                0 -> (3 + shape - 1) % 3
                1 -> (shape)
                2 -> (shape + 1) % 3
                else -> 0
            })
        }
    }
}