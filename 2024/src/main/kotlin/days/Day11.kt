package days

class Day11 : Day(11) {
    private val initialRocks = inputList.first().split(" ")
        .groupingBy { it }.eachCount().mapValues { (_, v) -> v.toLong() }

    override fun part1(): Any = run(25).map { it.value }.sum()
    override fun part2(): Any = run(75).map { it.value }.sum()

    private fun String.halve() = listOf(take(length / 2), drop(length / 2)).map { it.toLong().toString() }

    private fun run(times: Int) = (1..times).fold(initialRocks) { rocks, _ ->
        rocks.flatMap { (rock, count) ->
            when {
                rock == "0" -> listOf("1")
                rock.length % 2 == 0 -> rock.halve()
                else -> listOf((rock.toLong() * 2024).toString())
            }.map { it to count }
        }.groupBy { it.first }.mapValues { (_, v) -> v.sumOf { it.second } }
    }
}