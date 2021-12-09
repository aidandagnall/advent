package days

class Day09 : Day(9) {
    override fun part1() : Any {
        val input = inputList.map { it.chunked(1).map { i -> i.toInt() } }

        return input.mapIndexed { i, p ->
                p.mapIndexed { j, q ->
                    if (input.adjacent(i, j).all { q < it }) q + 1 else 0
                }
            }.flatten()
            .sum()
    }

    override fun part2() : Any {
        return 0
    }

    private fun <E> List<List<E>>.adjacent(x : Int, y : Int) : List<E> {
        val adjacent = mutableListOf<E>()
        if (x - 1 in indices) adjacent.add(this[x - 1][y])
        if (x + 1 in indices) adjacent.add(this[x + 1][y])
        if (y - 1 in this[0].indices) adjacent.add(this[x][y - 1])
        if (y + 1 in this[0].indices) adjacent.add(this[x][y + 1])
        return adjacent.toList()
    }
}
