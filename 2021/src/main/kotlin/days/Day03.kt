package days

class Day03 : Day(3) {

    override fun part1() : Any {
        val gamma = Integer.parseInt(
            (0 until inputList[0].length)
                .mapIndexed { i, _ -> inputList.mostCommonAt(i).first()}
                .joinToString(""),
            2,
        )
        val epsilon = Integer.parseInt(
            (0 until inputList[0].length)
                .mapIndexed { i, _ -> inputList.leastCommonAt(i).first() }
                .joinToString(""),
            2,
        )
        return gamma * epsilon
    }

    override fun part2() : Any {
        var oxygen = inputList
        var co2 = inputList
        for (i in 0 until inputList[0].length) {
            oxygen = oxygen.filter{
                val set = oxygen.mostCommonAt(i)
                if (set.size > 1) it[i] == '1' else it[i] in set
            }
            co2 = co2.filter{
                val set = co2.leastCommonAt(i)
                if (set.size > 1) it[i] == '0' else it[i] in set
            }
        }

        return Integer.parseInt(oxygen.first(), 2) * Integer.parseInt(co2.first(), 2)
    }

    /*
     * Return the list of most common characters of a list at a given index
     */
    private fun List<String>.mostCommonAt(index : Int) : List<Char> {
        val t = this.map { it[index] }
            .groupingBy { it }
            .eachCount()
        return t.filterValues { it == t.values.maxOrNull()!!}
            .keys.toList()
    }

    /*
     * Return the list of least common characters of a list at a given index
     */
    private fun List<String>.leastCommonAt(index : Int) : List<Char> {
        val t = this.map { it[index] }
            .groupingBy { it }
            .eachCount()
        return t.filterValues { it == t.values.minOrNull()!!}
            .keys.toList()
    }

}