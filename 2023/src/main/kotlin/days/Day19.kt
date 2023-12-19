package days

import util.replaceAt

class Day19 : Day(19) {

    sealed interface Rule
    data class Conditional(val value: PartType, val condition: Char, val target: Int, val destination: String): Rule {
        fun check(input: Int): Boolean = if (condition == '<') input < target else input > target
    }
    data class Base(val destination: String): Rule

    enum class PartType {
        X, M, A, S;

        companion object {
            fun fromString(str: String): PartType = when(str.first()) {
                'x' -> X
                'm' -> M
                'a' -> A
                's' -> S
                else -> error("Unexpected part type")
            }
        }
    }

    private val workflows = inputGroupedList.first().associate { line ->
        val name = line.substringBefore('{')
        val body = line.removePrefix("$name{").removeSuffix("}")
            .split(",")
            .map {
                when {
                    ':' !in it -> Base(it)
                    else -> """([xmas])([<>])(\d+):([a-zAR]+)""".toRegex().matchEntire(it)!!
                        .groupValues
                        .let {(_, a, b, c, d) ->
                            Conditional(PartType.fromString(a), b.first(), c.toInt(), d)
                        }
                }
            }
        name to body
    }.let {
        // this just means we only have to handle A and R in the base case
        it + ("A" to listOf(Base("A"))) + ("R" to listOf(Base("R")))
    }

    private val ratings = inputGroupedList.last().map {
        """\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}""".toRegex().matchEntire(it)!!
            .groupValues.drop(1).map(String::toInt)
    }

    override fun part1() : Any = ratings.sumOf { process(it, workflows["in"]!!) }

    override fun part2() : Any = findPaths((1..4).map { (1..4000).toList() }, workflows["in"]!!)

    private tailrec fun process(values: List<Int>, rules: List<Rule>): Int {
        rules.forEach {
            when(it) {
                is Conditional -> {
                    if (it.check(values[it.value.ordinal])) {
                        return process(values, workflows[it.destination]!!)
                    }
                }
                is Base -> return when(it.destination) {
                    "A" -> values.sum()
                    "R" -> 0
                    else -> process(values, workflows[it.destination]!!)
                }
            }
        }
        return 0
    }

    private fun findPaths(ranges: List<List<Int>>, rules: List<Rule>): Long {
        val continued = ranges.map { it.toMutableList() }
        return rules.fold(0L) { acc, rule ->
            when(rule) {
                is Conditional -> {
                    val index = rule.value.ordinal
                    acc + findPaths(
                        continued.replaceAt(index, continued[index].filter { rule.check(it) }),
                        workflows[rule.destination]!!
                    ).also { continued[index].removeIf { rule.check(it) } }
                }
                is Base -> acc + when(rule.destination) {
                    "A" -> continued.fold(1L) { combinations, it -> combinations * it.count() }
                    "R" -> 0
                    else -> findPaths(continued, workflows[rule.destination]!!)
                }
            }
        }
    }
}