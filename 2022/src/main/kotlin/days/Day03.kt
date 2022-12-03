package days

class Day03 : Day(3) {

    private val input = inputList.map {rucksack ->
        rucksack.map { it.toPriority() }
    }

    private fun Char.toPriority() : Int = if (this.isLowerCase()) this - 'a' + 1 else this - 'A' + 27

    override fun part1() : Any {
        return input.sumOf { rucksack ->
            val compartment1 = rucksack.take(rucksack.size / 2)
            val compartment2 = rucksack.drop(rucksack.size / 2)
            compartment1.first { it in compartment2 }
        }
    }

    override fun part2() : Any {
        return input.chunked(3).sumOf { (a, b, c) ->
            a.first { it in b && it in c }
        }
    }
}