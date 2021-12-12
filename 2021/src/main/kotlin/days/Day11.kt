package days

class Day11 : Day(11) {
    val flashed = mutableSetOf<Pair<Int, Int>>()
    override fun part1(): Any {
        var input = inputList.map { it.chunked(1).map { i -> i.toInt() }.toMutableList() }
        return (1..100).sumOf { _ ->
            flashed.clear()
            // increment
            input = input.map { row -> row.map { it + 1 }.toMutableList() }

            // handle flashes
            input.forEachIndexed { i, row ->
                row.forEachIndexed { j, _ ->
                    input.flash(i, j)
                }
            }

            // reset
            input = input.map { row -> row.map { if (it > 9) 0 else it }.toMutableList() }
            flashed.size
        }
    }

    override fun part2(): Any {
        var input = inputList.map { it.chunked(1).map { i -> i.toInt() }.toMutableList() }
        var simultaneous = false
        var step = 0

        while (!simultaneous) {
            // increment
            step++
            input = input.map { row -> row.map { it + 1 }.toMutableList() }

            // handle flashes
            input.forEachIndexed { i, row ->
                row.forEachIndexed { j, _ ->
                    input.flash(i, j)
                }
            }

            // reset and check if all flash
            simultaneous = input.all { row -> row.all { it > 9 } }
            input = input.map { row -> row.map { if (it > 9) 0 else it }.toMutableList() }
            flashed.clear()
        }
        return step
    }

    private fun List<MutableList<Int>>.flash(x: Int, y: Int) {
        if (this[x][y] <= 9 || (x to y) in flashed) return
        flashed.add(x to y)
        (x - 1..x + 1).forEach { i ->
            (y - 1..y + 1).forEach inner@{ j ->
                if ((i to j) == (x to y)) return@inner
                if (i in indices && j in get(0).indices) {
                    this[i][j] += 1
                    this.flash(i, j)
                }
            }
        }
    }
}