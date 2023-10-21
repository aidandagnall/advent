package days

class Day01 : Day(1) {
    override fun part1() : Any = inputList.sumOf { it.toInt() }

    override fun part2() : Any {
        var freq = 0
        val prev = mutableSetOf<Int>(0)

        while(true) {
            inputList.forEach {
                freq += it.toInt()
                if (freq in prev) {
                    return freq
                }
                prev.add(freq)
            }
        }
    }
}