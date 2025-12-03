package days

class Day03 : Day(3) {
    override fun part1() : Any = inputList.sumOf { it.jolt(2) }
    override fun part2() : Any = inputList.sumOf { it.jolt(12) }

    fun String.jolt(capacity: Int): Long {
        val choices = take(count() - capacity + 1)
        val index = choices.indexOf(choices.max())

        if (capacity == 1) {
            return choices[index].toString().toLong()
        }
        return "${choices[index]}${drop(index + 1).jolt(capacity - 1)}".toLong()
    }
}