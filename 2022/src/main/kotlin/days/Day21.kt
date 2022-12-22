package days

class Day21 : Day(21) {

    private val monkeys = inputList.map { it.split(": ") }.map { (a, b) -> Monkey(a, b)}
    private val shouted = mutableMapOf<String, Long>()
    private val subequations = mutableMapOf<String,String>()
    override fun part1() : Any {
        while("root" !in shouted) {
            monkeys.forEach { (name, op) ->
                if (name in shouted) return@forEach

                if (listOf('+', '-', '*', '/').any { it in op }) {
                    val components = op.split(" ")

                    if (components[0] in shouted && components[2] in shouted) {
                        shouted[name] = compute(components[0], components[1], components[2])
                    }
                } else {
                    shouted[name] = op.toLong()
                }
            }
        }

        return shouted["root"]!!
    }

    override fun part2() : Any {
        shouted.clear()
        while("root" !in shouted) {
            monkeys.forEach { (name, op) ->
                if (name in shouted) return@forEach

                if (listOf('+', '-', '*', '/').any { it in op }) {
                    val components = op.split(" ")

                    if (components[0] in shouted && components[2] in shouted) {
                        shouted[name] = compute(components[0], components[1], components[2])

                        val a = if (components[0] == "humn") "x" else subequations[components[0]]!!
                        val b = if (components[2] == "humn") "x" else subequations[components[2]]!!
                        val o = if (name == "root") "=" else components[1]
                        subequations[name] = if (!a.contains("x") && !b.contains("x")) {
                            "${shouted[name]!!}"
                        } else {
                            if ("x" in a) "($a)$o${shouted[components[2]]!!}" else "${shouted[components[0]]!!}$o($b)"
                        }
                    }
                } else {
                    shouted[name] = op.toLong()
                    subequations[name] = op
                }
            }
        }
        return subequations["root"]!!.simplifyEquation().split("=").last().toLong()
    }

    private fun compute(a: String, op: String, b: String): Long =  when(op) {
        "+" -> shouted[a]!! + shouted[b]!!
        "-" -> shouted[a]!! - shouted[b]!!
        "*" -> shouted[a]!! * shouted[b]!!
        "/" -> shouted[a]!! / shouted[b]!!
        else -> 0
    }

    private fun String.simplifyEquation(): String {
        if (listOf('+', '-', '*', '/').all { it !in this }) return this

        val (a, b) = split("=")
        val result = (if ("x" in a) b else a).replace("(", "").replace(")", "")
        val equation = (if ("x" in a) a else b).drop(1).dropLast(1)
        if (equation == "(x)") return "x=$result"

        var depth = 0
        val first = equation.takeWhile {
            when (it) {
                '(' -> {
                    depth++
                    true
                }
                ')' -> {
                    depth--
                    true
                }
                '+', '-', '*', '/' -> depth != 0
                else -> true
            }
        }

        val op = equation[first.length]
        val second = equation.drop(1 + first.length)

        return when(op) {
            '+' -> {
                if ("x" in first) {
                    "$first=${result.toLong() - second.toLong()}"
                } else {
                    "$second=${result.toLong() - first.toLong()}"
                }
            }
            '-' -> {
                if ("x" in first) {
                    "$first=${result.toLong() + second.toLong()}"
                } else {
                    "$second=${first.toLong() - result.toLong()}"
                }
            }
            '*' -> {
                if ("x" in first) {
                    "$first=${result.toLong() / second.toLong()}"
                } else {
                    "$second=${result.toLong() / first.toLong()}"
                }
            }
            '/' -> {
                if ("x" in first) {
                    "$first=${result.toLong() * second.toLong()}"
                } else {
                    "$second=${result.toLong() / first.toLong()}"
                }
            }

            else -> throw Exception("Cannot simplify $this")
        }.simplifyEquation()
    }

    data class Monkey(val name: String, val operation: String)
}