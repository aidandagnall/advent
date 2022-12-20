package days

class Day20 : Day(20) {

    private val input = inputList.map { it.toLong() }
    private val message = input.mapIndexed { index, i -> index to i }.toMutableList()
    private val decrypted = input.mapIndexed { index, i -> index to (i * 811589153L)}.toMutableList()

    override fun part1() : Any {
        message.doRound()
        val zero = message.indexOfFirst { it.second == 0L }
        return listOf(1000, 2000, 3000).sumOf { message[(zero + it) % message.size].second }
    }

    override fun part2() : Any {
        repeat(10) {
            decrypted.doRound()
        }
        val zero = decrypted.indexOfFirst { it.second == 0L }
        return listOf(1000, 2000, 3000).sumOf { decrypted[(zero + it) % decrypted.size].second }
    }

    private fun MutableList<Pair<Int, Long>>.doRound() {
        input.indices.forEach { i ->
            val index = indexOfFirst { it.first == i }
            val (order, value) = removeAt(index)
            add(((size + index + (value % size)) % size).toInt(), order to value)
        }
    }
}