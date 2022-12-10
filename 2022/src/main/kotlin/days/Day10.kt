package days

class Day10 : Day(10) {
    private val signals = mutableListOf(1, 1)

    init {
        var x = 1
        var acc : Int? = null

        val input = inputList.toMutableList()
        while (input.isNotEmpty()) {
            // pending addx op, so add this cycle
            if (acc != null) {
                x += acc
                acc = null
                signals.add(x)
                continue
            }

            // no pending addx op, read a new instruction
            val ins = input.removeFirst()
             if ("addx" in ins){
                ins.split(" ").let { (_, b) ->
                    acc = b.toInt()
                }
            }
            signals.add(x)
        }
    }

    override fun part1() : Any {
        return listOf(20, 60, 100, 140, 180, 220).sumOf { signals[it] * it }
    }

    override fun part2() : Any {
        return "\n" + (0..5).joinToString("\n") { row ->
            (0..39).joinToString("") { col ->
                val cycle = (40 * row) + col + 1
                if (kotlin.math.abs(signals[cycle] - col) <= 1) {
                    "#"
                } else "."
            }

        }
    }
}