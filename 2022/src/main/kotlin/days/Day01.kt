package days

class Day01 : Day(1) {

    private val elves = inputList.fold(mutableListOf(0)) { acc, s ->
        acc.also {
            when {
                s.isEmpty() -> acc.add(0)
                else -> acc[acc.lastIndex] += s.toInt()
            }
        }
    }
    override fun part1() : Any {
        return elves.maxOf { it }
    }

    override fun part2() : Any {
        return elves.sorted().takeLast(3).sum()
    }
}