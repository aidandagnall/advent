package days

class Day06 : Day(6) {
    override fun part1() : Any {
        return inputList.first().windowed(4)
            .map { it.toList().distinct() }
            .indexOfFirst { it.size == 4 } + 4
    }

    override fun part2() : Any {
        return inputList.first().windowed(14)
            .map { it.toList().distinct() }
            .indexOfFirst { it.size == 14 } + 14
    }
}