package days

import util.product

class Day11 : Day(11) {

    private val regex = """
        Monkey (\d+):\s+
        Starting items: (\d+(?:[ \t]*,[ \t]*\d+)*)\s+
        Operation: new = old ([+*] \S+)\s+Test: divisible by (\d+)\s+
        If true: throw to monkey (\d+)\s+
        If false: throw to monkey (\d+)
    """.trimIndent().replace("\n", "").toRegex()
    private val lcm = getMonkeys().map { it.test }.fold(1) { acc, i -> acc * i }

    override fun part1() : Any = (0 until 20).fold(getMonkeys()) { acc, _ -> doRound(acc, true)}
            .map { it.inspections }.sorted().takeLast(2).product()

    override fun part2() : Any = (0 until 10_000).fold(getMonkeys()){ acc, _ -> doRound(acc, false)}
            .map { it.inspections }.sorted().takeLast(2).product()

    private fun getMonkeys(): List<Monkey> {
        return inputGroupedList.map { monkey ->
            regex.matchEntire(monkey.joinToString(" "))!!.groupValues.let {
                Monkey(
                    it[1].toInt(),
                    it[2].split(", ").map(String::toInt).toMutableList(),
                    it[3],
                    it[4].toInt(),
                    it[5].toInt(),
                    it[6].toInt(),
                )
            }
        }
    }

    private fun doRound(monkeys: List<Monkey>, reduceWorry: Boolean): List<Monkey> {
        return monkeys.map { monkey ->
            (0 until monkey.items.size).forEach { _ ->
                val worryValue = (monkey.inspect().let {
                    if (reduceWorry) it / 3 else it
                } % lcm).toInt()

                val destination = if (worryValue % monkey.test == 0) monkey.trueCase else monkey.falseCase
                monkeys[destination].items.add(worryValue)
            }
            monkey
        }
    }

    data class Monkey(
        val index: Int,
        val items : MutableList<Int>,
        val operation: String,
        val test: Int,
        val trueCase: Int,
        val falseCase: Int,
        var inspections: Long = 0L,
    ) {
        fun inspect(): Long {
            val initial = items.removeFirst().toLong()
            inspections++
            val (op, value) = operation.split(" ").let { (a, b) ->
                    a to if (b == "old") initial else b.toLong()
            }

            return when (op) {
                "+" -> initial + value
                "*" -> initial * value
                else -> initial + value
            }
        }
    }
}