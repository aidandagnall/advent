package days

import util.replaceAt

class Day19 : Day(19) {

    sealed interface Rule
    data class Conditional(val value: PartType, val condition: Char, val target: Int, val destination: String): Rule
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

    val workflows = inputGroupedList.first().associate { line ->
        val name = line.substringBefore('{')
        val body = line.removePrefix("$name{").removeSuffix("}")
            .split(",")
            .map {
                if (':' !in it) {
                    Base(it)
                } else {
                    """([xmas])([<>])(\d+):([a-zAR]+)""".toRegex().matchEntire(it)!!
                        .groupValues
                        .let {(_, a, b, c, d) ->
                            Conditional(PartType.fromString(a), b.first(), c.toInt(), d)
                        }
                }
            }
        name to body
    }.let {
        it + ("A" to listOf(Base("A"))) + ("R" to listOf(Base("R")))
    }

    private val ratings = inputGroupedList.last().map {
        """\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}""".toRegex().matchEntire(it)!!
            .groupValues.let { (_, x, m, a, s) ->
                listOf(
                    x.toInt(), m.toInt(), a.toInt(), s.toInt()
                )
            }
    }

    override fun part1() : Any = ratings.sumOf {
            process(it, workflows["in"]!!)
        }

    override fun part2() : Any = findPaths(
            listOf(
                (1..4000).toList(),
                (1..4000).toList(),
                (1..4000).toList(),
                (1..4000).toList(),
            ),
            workflows["in"]!!
        )

    private tailrec fun process(values: List<Int>, rules: List<Rule>): Int {
        rules.forEach {
            when(it) {
                is Conditional -> {
                    val value = values[it.value.ordinal]
                    val result = if (it.condition == '<') value < it.target else value > it.target
                    if (result) {
                        return when(it.destination) {
                            "A" -> values.sum()
                            "R" -> 0
                            else -> process(values, workflows[it.destination]!!)
                        }
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
                    acc + if (rule.condition == '<') {
                        findPaths(
                            continued.replaceAt(index, continued[index].filter { it < rule.target }),
                            workflows[rule.destination]!!
                        ).also { continued[index].removeIf { it < rule.target } }
                    } else {
                        findPaths(
                            continued.replaceAt(index, continued[index].filter { it > rule.target }),
                            workflows[rule.destination]!!
                        ).also { continued[index].removeIf { it > rule.target } }
                    }
                }
                is Base -> acc + when(rule.destination) {
                    "A" -> continued.fold(1L) { acc, it -> acc * it.count() }
                    "R" -> 0
                    else -> findPaths(continued, workflows[rule.destination]!!)
                }
            }
        }
    }
}