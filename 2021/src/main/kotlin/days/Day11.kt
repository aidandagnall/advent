package days

class Day11 : Day(11) {

    override fun part1(): Any {
        var input = inputList.map { it.chunked(1).map { i -> i.toInt() }.toMutableList() }
        return (1..100).sumOf { _ ->
            input.step().let { (newInput, flashed) ->
                input = newInput
                flashed
            }
        }
    }

    override fun part2(): Any {
        var input = inputList.map { it.chunked(1).map { i -> i.toInt() }.toMutableList() }
        return generateSequence {
            input.step().let { (newInput, newFlashed) ->
                input = newInput
                newFlashed
            }
        }.takeWhile { it < inputList.sumOf { i -> i.length } }
        .count() + 1
    }

    private fun List<MutableList<Int>>.step(): Pair<List<MutableList<Int>>,Int> {
        val flashed = mutableSetOf<Pair<Int,Int>>()
        return map { row -> row.map { it + 1 }.toMutableList() }.apply {
            forEachIndexed { i, row ->
                row.forEachIndexed { j, _ ->
                    flash(i, j, flashed)
                }
            }
        }.map { row -> row.map { if (it > 9) 0 else it }.toMutableList() } to flashed.size
    }
    private fun List<MutableList<Int>>.flash(x: Int, y: Int, flashed: MutableSet<Pair<Int,Int>>) {
        if (this[x][y] <= 9 || (x to y) in flashed) return
        flashed.add(x to y)
        (x - 1..x + 1).flatMap { i -> (y - 1..y + 1).map { j -> i to j } }
            .filter { (i, j) -> i to j != x to y }
            .filter { (i, j) -> i in indices && j in get(0).indices }
            .forEach { (i,j) ->
                this[i][j] += 1
                this.flash(i, j, flashed)
            }
    }
}