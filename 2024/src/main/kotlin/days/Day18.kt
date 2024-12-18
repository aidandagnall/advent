package days

import util.neighbours4

class Day18 : Day(18) {
    private val input = inputList.map { it.split(",").let { (a, b) -> a.toInt() to b.toInt() } }
    private val start = 0 to 0
    private val end = 70 to 70

    override fun part1() : Any = search(input.take(1024).toSet())
    override fun part2() : Any =
        input.indices.toList().windowed(2).binarySearch { (a, b) ->
            if (search(input.take(a).toSet()) > 0) {
                if (search(input.take(b).toSet()) == -1) 0 else -1
            } else 1
        }.let { input[it] }.let { (x, y) -> "$x,$y" }

    private fun search(walls: Set<Pair<Int,Int>>): Int {
        val scores = mutableMapOf(start to 0)
        val queue = mutableListOf(start)
        val seen = mutableSetOf(start)

        while(queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current == end) return scores[current]!!

            current.neighbours4()
                .filter { it !in seen && it !in walls }
                .filter { (x, y) -> x in 0..70 && y in 0..70 }
                .forEach {
                    queue.add(it)
                    seen.add(it)
                    scores[it] = scores[current]!! + 1
                }
        }
        return -1
    }
}