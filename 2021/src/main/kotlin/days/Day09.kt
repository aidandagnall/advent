package days

class Day09 : Day(9) {

    private val input = inputList.map { it.chunked(1).map { i -> i.toInt() } }

    override fun part1() : Any {
        return input.mapIndexed { i, p ->
                p.mapIndexed { j, q ->
                    if (input.adjacent(i, j).all { q < it.second }) q + 1 else 0
                }
            }.flatten()
            .sum()
    }

    override fun part2() : Any {
        val lows = mutableSetOf<Pair<Int, Int>>()
        input.forEachIndexed { i, p ->
            p.forEachIndexed { j, q ->
                if (input.adjacent(i, j).all { q < it.second }) lows.add(i to j)
            }
        }
        return lows.map { flood(setOf(it)).size }
            .sorted()
            .takeLast(3)
            .fold(1) { acc, next -> acc * next }
    }

    private fun flood( currentBasin : Set<Pair<Int, Int>> ) : Set<Pair<Int, Int>> {
        val newBasin = currentBasin.toMutableSet()
        currentBasin.forEach { (x, y) ->
            input.adjacent(x, y).forEach { (position , value) ->
                if (value < 9) newBasin.add(position)
            }
        }

        if (currentBasin.size == newBasin.size) return newBasin
        return flood(newBasin)
    }

    private fun <E> List<List<E>>.adjacent(x : Int, y : Int) : List<Pair<Pair<Int, Int>, E>> {
        val adjacent = mutableListOf<Pair<Pair<Int, Int>, E>>()
        if (x - 1 in indices) adjacent.add((x - 1 to y) to this[x - 1][y])
        if (x + 1 in indices) adjacent.add((x + 1 to y) to this[x + 1][y])
        if (y - 1 in this[0].indices) adjacent.add((x to y - 1) to this[x][y - 1])
        if (y + 1 in this[0].indices) adjacent.add((x to y + 1) to this[x][y + 1])
        return adjacent.toList()
    }
}
