package days

class Day03 : Day(3) {

    private val symbols = inputList.mapIndexed { y, it ->
        it.mapIndexedNotNull { x, c ->
            if (!c.isDigit() && c != '.') (x to y) to c
            else null
        }
    }.flatten().toMap()

    private val numbers = inputList.flatMapIndexed { y, line ->
        "(\\d+)".toRegex().findAll(line).map { match ->
            match.range.map { it to y } to match.value.toInt()
        }
    }

    override fun part1() : Any =
        numbers
            .filter { (coords, _) -> coords.any { n -> n.neighbours().any { it in symbols } } }
            .sumOf { it.second }


    override fun part2() : Any =
        symbols.filter { (_, v) -> v == '*' }
            .map { (k, _) ->
                numbers.filter { (coords, _) -> k.neighbours().any { it in coords} }
            }.filter { it.count() == 2 }
            .sumOf { (a, b) -> a.second * b.second }

    private fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> =
        (-1..1).flatMap { x ->
            (-1..1).map { y ->
                this.first + x to this.second + y
            }
        }
}