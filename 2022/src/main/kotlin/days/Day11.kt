package days

class Day11 : Day(11) {

    private val lcm = getMonkeys().map { it.test }.fold(1L) { acc, i -> acc * i }

    override fun part1() : Any = (0 until 20).fold(getMonkeys()) { acc, _ -> doRound(acc, true)}
            .map { it.inspections }
            .sorted()
            .takeLast(2)
            .let { (a, b) -> a * b }

    override fun part2() : Any = (0 until 10_000).fold(getMonkeys()){ acc, _ -> doRound(acc, false)}
            .map { it.inspections }
            .sorted()
            .takeLast(2)
            .let { (a, b) -> a * b }

    private fun getMonkeys(): List<Monkey> {
        var mutableInput = inputList.toMutableList()
        val monkeyList = mutableListOf<Monkey>()
        while (mutableInput.size > 0) {
            val monkeyInput = mutableInput.takeWhile { it != "" }
            mutableInput = mutableInput.drop(monkeyInput.size + 1).toMutableList()

            monkeyList.add( Monkey(
                index = monkeyInput[0].trim().removePrefix("Monkey ").removeSuffix(":").toInt(),
                items = monkeyInput[1].trim().removePrefix("Starting items: ").split(", ").map { it.toLong() }.toMutableList(),
                operation = monkeyInput[2].trim().removePrefix("Operation: new = "),
                test = monkeyInput[3].trim().removePrefix("Test: divisible by ").toInt(),
                trueCase = monkeyInput[4].trim().removePrefix("If true: throw to monkey ").toInt(),
                falseCase = monkeyInput[5].trim().removePrefix("If false: throw to monkey ").toInt(),
            ))
        }

        return monkeyList.toList()
    }

    private fun doRound(monkeys: List<Monkey>, reduceWorry: Boolean): List<Monkey> {
        return monkeys.map { monkey ->
            (0 until monkey.items.size).forEach { _ ->
                val worryValue = monkey.inspect().let {
                    if (reduceWorry) it / 3 else it
                } % lcm

                val destination= if (worryValue % monkey.test == 0L) monkey.trueCase else monkey.falseCase
                monkeys[destination].items.add(worryValue)
            }
            monkey
        }
    }

    data class Monkey(
        val index: Int,
        val items : MutableList<Long>,
        val operation: String,
        val test: Int,
        val trueCase: Int,
        val falseCase: Int,
        var inspections: Long = 0L,
    ) {
        fun inspect(): Long {
            val initial = items.removeFirst()
            inspections++
            val (a, op, b) = operation.split(" ")
                .let { (a, b, c) ->
                    Triple(
                        if (a == "old") initial else a.toLong(),
                        b,
                        if (c == "old") initial else c.toLong(),
                    )
                }

            return when (op) {
                "+" -> a + b
                "*" -> a * b
                "-" -> a - b
                "/" -> a / b
                else -> a + b
            }

        }
    }

}