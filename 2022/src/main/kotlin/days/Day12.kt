package days
class Day12 : Day(12) {

    private val input = inputList.map { it.toList() }
    private val start = input.indexOfFirst { it.contains('S') }.let { x ->
        x to input[x].indexOfFirst { it == 'S' }
    }
    private val goal = input.indexOfFirst { it.contains('E') }.let { x ->
        x to input[x].indexOfFirst { it == 'E' }
    }

    override fun part1() : Any {
        return search(start, 'E', true)
    }

    override fun part2() : Any {
        return search(goal, 'a', false)
    }

    private fun Char.elevation(): Char = when (this) {
        'S' -> 'a'
        'E' -> 'z'
        else -> this
    }

    private fun search(start: Pair<Int,Int>, target: Char, ascending: Boolean): Int {
        val queue = mutableListOf<Pair<Int,Int>>(start)
        val distances = mutableMapOf<Pair<Int,Int>, Int>()
        val visited = mutableSetOf<Pair<Int,Int>>()
        distances[start] = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current !in visited) {
                visited.add(current)

                val currentElevation = input[current.first][current.second].elevation()

                current.neighbours().filter { (x, y) ->
                    val destElevation = input[x][y].elevation()
                    if (ascending) {
                        currentElevation - destElevation >= -1
                    } else {
                        destElevation - currentElevation >= -1
                    }
                }.filter { it !in visited }.forEach {
                    if (input[it.first][it.second] == target) {
                        return distances[current]!! + 1
                    }

                    if (it !in distances) {
                        distances[it] = distances[current]!! + 1
                        queue.add(it)
                    }
                }
            }
        }
        return Int.MAX_VALUE
    }

    private fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> {
        return listOf(
            this.first - 1 to this.second,
            this.first + 1 to this.second,
            this.first to this.second - 1,
            this.first to this.second + 1,
        ).mapNotNull { (x, y) -> if (x in input.indices && y in input[0].indices) x to y else null }
    }
}