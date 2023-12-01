package days

class Day11 : Day(11) {
    private val gridId = inputList.first().toInt()
    private fun powerLevel(x: Int, y: Int): Int = (((((((x.toLong() + 10L) * y.toLong()) + gridId.toLong()) * (x.toLong() + 10L)) % 1000) / 100) - 5).toInt()

    override fun part1() : Any = run()
    override fun part2() : Any = run(300)

    private fun run(size: Int? = null): Pair<Triple<Int,Int,Int>, Int> {
        val cache = mutableMapOf<Triple<Int,Int,Int>, Int>()
        fun hitCache(x: Int, y: Int, s: Int): Int = when {
            s <= 3 -> (0 until s).sumOf { a ->
                    (0 until s).sumOf { b ->
                        powerLevel(x + a, y + b)
                    }
                }
            s % 2 == 0 -> cache[Triple(x, y, s / 2)]!! +
                    cache[Triple(x + (s / 2), y, s / 2)]!! +
                    cache[Triple(x , y + (s / 2), s / 2)]!! +
                    cache[Triple(x + (s / 2) , y + (s / 2), s / 2)]!!
            else -> cache[Triple(x, y, s - 1)]!! + (0 until s - 1).sumOf { size ->
                    powerLevel(x + s - 1, y + size) + powerLevel(x + size, y + s - 1)
                } + powerLevel(x + s - 1, y + s - 1)
        }

        fun explore(s: Int = 3): Pair<Triple<Int,Int,Int>, Int> {
            return (1..300 - s + 1).flatMap { x ->
                (1..300 - s + 1).map { y ->
                    val result = hitCache(x, y, s)
                    cache[Triple(x, y, s)] = result
                    Triple(x, y, s) to result
                }
            }.maxBy { it.second }
        }

        return if (size == null) explore()
        else generateSequence(1) { it + 1 }.map { s ->
            explore(s)
        }.windowed(2).takeWhile { (a, b) -> a.second < b.second }.last().last()
    }
}