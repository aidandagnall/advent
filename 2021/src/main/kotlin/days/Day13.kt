package days

class Day13 : Day(13) {
    private val grid = inputList.takeWhile { it != "" }
        .map { it.split(',').let { (axis, line) -> axis.toInt() to line.toInt() } }

    private val folds = inputList.dropWhile { !it.contains("fold") }
        .map { it.split(' ')[2].split('=') }
        .map { it[0] to it[1].toInt() }

    override fun part1() : Any {
        return folds.take(1)
            .fold(grid) { acc, (axis, line) -> acc.foldGrid(axis, line) }
            .distinct().size
    }

    override fun part2() : Any {
        return folds
            .fold(grid) { acc, (axis, line) -> acc.foldGrid(axis, line)}
            .plot()
    }

    private fun List<Pair<Int,Int>>.foldGrid(axis: String, line: Int) : List<Pair<Int,Int>> {
        return map { (x, y) ->
            when {
                y > line && axis == "y" -> x to line - (y - line)
                x > line && axis == "x" -> line - (x - line) to y
                else -> x to y
            }
        }
    }

    private fun List<Pair<Int,Int>>.plot() : String {
        return "\n" + (0..this.maxOf { it.second }).joinToString("\n") { y ->
            (0..this.maxOf { it.first }).map { x ->
                if (x to y in this) '#'
                else ' '
            }.joinToString("")
        }
    }
}