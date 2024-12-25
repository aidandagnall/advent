package days

class Day24 : Day(24) {

    val inputs = inputList.takeWhile { it != "" }
        .associate { it.substringBefore(": ") to it.substringAfter(": ") }

    val gates = inputList.drop(1).takeLastWhile { it != "" }
        .map {
            val (gate, out) = it.split(" -> ")
            val (a, type, b) = gate.split(" ")
            Gate(a, b, type, out)
        }.toSet()

    data class Gate(val input1: String, val input2: String, val type: String, val out: String)

    override fun part1() : Any = calculate(gates)
    override fun part2() : Any {
        // visualise
        gates.forEach { (i1, i2, t, out) ->
            val colour = when(t) {
                "AND" -> "red"
                "OR" -> "green"
                "XOR" -> "blue"
                else -> error("")
            }
            println("$i1 -> $out [color=$colour];")
            println("$i2 -> $out [color=$colour];")
        }

        // manually found these
        val fixes = mutableMapOf("vcf" to "z10", "dvb" to "fsq", "tnc" to "z39", "z17" to "fhg")

        // correct the gates
        val newGates = gates.map { gate ->
            when(gate.out) {
                in fixes -> gate.copy(out = fixes[gate.out]!!)
                in fixes.values -> gate.copy(out = fixes.toList().associate { it.second to it.first }[gate.out]!!)
                else -> gate
            }
        }.toSet()

        require(findIncorrectBits(newGates) == emptyList<Int>())

        return fixes.flatMap { listOf(it.key, it.value) }.sorted().joinToString(",")
    }


    private fun calculate(gates: Set<Gate>): Long {
        val results = inputs.toMutableMap()
        val queue = gates.toMutableList()

        while(queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current.input1 !in results || current.input2 !in results) {
                queue.add(current)
                continue
            }

            val result = when (current.type) {
                "AND" -> if(results[current.input1] == "1" && results[current.input2] == "1") "1" else "0"
                "OR" -> if(results[current.input1] == "1" || results[current.input2] == "1") "1" else "0"
                "XOR" -> if(results[current.input1] != results[current.input2]) "1" else "0"
                else -> error("")

            }
            results[current.out] = result
        }

        return results.getNumberedBits { it.startsWith("z") }
    }

    private fun Map<String,String>.getNumberedBits(p: (String) -> Boolean) = filter { p(it.key) }
        .toList().sortedByDescending { it.first }
        .joinToString("") { it.second }.toLong(2)

    private fun findIncorrectBits(gates: Set<Gate>): List<Int> {
        val x = inputs.getNumberedBits { it.startsWith("x") }
        val y = inputs.getNumberedBits { it.startsWith("y") }

        val result = x + y

        val expected = result.toString(2)
        val actual = calculate(gates).toString(2)

        val joined = expected.zip(actual)

        return (joined.size - 1 downTo 0).filter {
            val (a, b) = joined[it]
            a != b
        }
    }
}