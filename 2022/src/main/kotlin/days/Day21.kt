package days

class Day21 : Day(21) {

    val monkeys = inputList.map { it.split(": ") }.map { (a, b) -> Monkey(a, b)}
    val shouted = mutableMapOf<String, Long>()
    val subequations = mutableMapOf<String,String>()
    override fun part1() : Any {
        while("root" !in shouted) {

            monkeys.forEach { (name, op) ->
                if (name in shouted) return@forEach

                if (listOf('+', '-', '*', '/').any { it in op }) {
                    val components = op.split(" ")

                    if (components[0] in shouted && components[2] in shouted) {

                        shouted[name] = when(components[1]) {
                            "+" -> shouted[components[0]]!! + shouted[components[2]]!!
                            "-" -> shouted[components[0]]!! - shouted[components[2]]!!
                            "*" -> shouted[components[0]]!! * shouted[components[2]]!!
                            "/" -> shouted[components[0]]!! / shouted[components[2]]!!
                            else -> 0
                        }

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

                        shouted[name] = when(components[1]) {
                            "+" -> shouted[components[0]]!! + shouted[components[2]]!!
                            "-" -> shouted[components[0]]!! - shouted[components[2]]!!
                            "*" -> shouted[components[0]]!! * shouted[components[2]]!!
                            "/" -> shouted[components[0]]!! / shouted[components[2]]!!
                            else -> 0
                        }
                        val a = if (components[0] == "humn") "x" else subequations[components[0]]!!
                        val b = if (components[2] == "humn") "x" else subequations[components[2]]!!
                        val o = if (name == "root") "=" else components[1]
                        subequations[name] = if (!a.contains("x") && !b.contains("x")) {
                            "${shouted[name]!!}"
                        } else {
                            if ("x" in a) "($a)$o(${shouted[components[2]]!!})" else "(${shouted[components[0]]!!})$o($b))"
                        }
                    }
                } else {
                    shouted[name] = op.toLong()
                    subequations[name] = op
                }
            }
        }

        println(subequations["root"])
        return subequations["root"]!!.simplifyEquation().split("=").last()
    }

    fun String.simplifyEquation(): String {
        if (listOf('+', '-', '*', '/').all { it !in this }) return this
        val (a, b) = split("=")
        val result = (if ("x" in a) b else a).replace("(", "").replace(")", "")
        val equation = (if ("x" in a) a else b).drop(1).dropLast(1)

        var depth = 0
        println(equation)
        println(equation.count { it == '('})
        println(equation.count { it == ')'})
        var mismatched = (equation.count { it == '('} > equation.count { it == ')'})
        val first = equation.drop(if (mismatched) 1 else 0).takeWhile {
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

        println(first)
        val op = equation[first.length + if(mismatched) 1 else 0]
        val second = equation.drop((if(mismatched) 2 else 1) + first.length)

        println("eq $equation")
        println("first $first")
        println("op $op")
        println("second $second")

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
                    "$second=${result.toLong() + first.toLong()}"
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
        println("eq $equation")
        println("first $first")
        println("op $op")
        println("second $second")
                    "$second=${result.toLong() / first.toLong()}"
                }
            }

            else -> throw Exception("Cannot simplify $this")
        }.also { println(it) }.simplifyEquation()
    }


    data class Monkey(val name: String, val operation: String)
}