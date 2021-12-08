package days

import kotlin.math.sign

class Day08 : Day(8) {
    private val regex = Regex("""^((?:[a-g]+ ){10})\|((?: [a-g]+){4})""")
    override fun part1() : Any {
        return inputList.sumOf { line ->
            line.split(" | ")[1]
                .split(" ")
                .count { it.length in listOf(2,3,4,7) }
        }
    }

    override fun part2() : Any {
        return inputList.sumOf { line ->
            val (signal, output) = line.split(" | ").map { it.split(" ") }
            val wirings = signal.associate { it.length to it.toCharArray().toSet() }

            // method from u/4HbQ, my original implementation was far less elegant
            // uses the triple (length, size of intersection with signal 4, size of intersection with signal 2) which
            // can be used to determine all other values
            // if only kotlin had nicer pattern matching (and wildcards) in when statements...
            output.joinToString("") {
                val inputSet = it.toCharArray().toSet()
                val v = Triple(it.length, inputSet.intersect(wirings[4]!!).size, inputSet.intersect(wirings[2]!!).size)
                when (v.first) {
                    2 -> "1"
                    3 -> "7"
                    4 -> "4"
                    7 -> "8"
                    5 -> when (v.second) {
                        2 -> "2"
                        3 -> if (v.third == 1) "5" else "3"
                        else -> "-"
                    }
                    6 -> when (v.second) {
                        4 -> "9"
                        3 -> if (v.third == 1) "6" else "0"
                        else -> "-"
                    }
                    else -> "-"
                }
            }.toInt()

        }
    }
}