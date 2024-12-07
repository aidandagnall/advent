package days

class Day07 : Day(7) {

    private val calibrations = inputList.map {
        it.split(": ").let { (a,b) ->
            a.toLong() to b.split(" ").map { it.toLong() }
        }
    }

    override fun part1() : Any = calibrations.filter { (res, oth) ->
            solve(res, oth, listOf(::add, ::mult))
        }.sumOf { it.first }

    override fun part2() : Any = calibrations.filter { (res, oth) ->
            solve(res, oth, listOf(::add, ::mult, ::join))
        }.sumOf { it.first }


    fun add(a: Long, b: Long) = a + b
    fun mult(a: Long, b: Long) = a * b
    fun join(a: Long, b: Long) = (a.toString() + b.toString()).toLong()

    fun solve(result: Long, others: List<Long>, operators: List<(Long, Long) -> Long>): Boolean {
        if (others.size == 1) {
            return others[0] == result
        }

        return operators.any {
            solve(result, listOf(it(others[0], others[1])) + others.drop(2), operators)
        }
    }
}