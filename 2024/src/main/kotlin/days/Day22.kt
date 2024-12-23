package days

class Day22 : Day(22) {
    private val input = inputList.map { it.toLong() }

    private val sequences = input.map { init ->
        generateSequence(init) { it.calculateNext() }.take(2001)
    }

    override fun part1() : Any = sequences.sumOf { it.last() }

    private fun Long.calculateNext() =
        let { (it * 64).mix(it).prune() }
            .let { (it / 32).mix(it).prune() }
            .let { (it * 2048).mix(it).prune() }

    private fun Long.mix(original: Long) = this xor original
    private fun Long.prune() = this % 16777216

    private val cache = mutableMapOf<List<Short>, MutableMap<Int, Int>>()
    override fun part2() : Any {
        val t = sequences.mapIndexed { i, it ->
            it.map { it % 10 }
                .windowed(2)
                .map { (a, b) -> b.toInt() to (b.toShort() - a.toShort()).toShort() }
                .windowed(4)
                .let {
                    it.toList().reversed().associate { it.map { it.second } to it.last().first }
                }
        }

        return cache.maxOf { (_, out) -> out.toList().sumOf { it.second } }
    }
}