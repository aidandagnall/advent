package days

class Day14 : Day(14) {
    private val count = inputList.first().toInt()
    override fun part1() : Any {
        val recipes = mutableListOf(3, 7)
        var elf1 = 0
        var elf2 = 1
        while(recipes.size < count + 10) {
            val sum = recipes[elf1] + recipes[elf2]
            if (sum >= 10) {
                recipes.add(sum / 10)
            }
            recipes.add(sum % 10)

            elf1 = (elf1 + 1 + recipes[elf1]) % recipes.size
            elf2 = (elf2 + 1 + recipes[elf2]) % recipes.size
        }

        return recipes.drop(count).take(10).joinToString("")
    }

    override fun part2() : Any {
        val recipes = mutableListOf(3, 7)
        var elf1 = 0
        var elf2 = 1
        while(!recipes.takeLast(count.toString().length + 1).joinToString("").contains(count.toString())) {
            val sum = recipes[elf1] + recipes[elf2]
            if (sum >= 10) {
                recipes.add(sum / 10)
            }
            recipes.add(sum % 10)

            elf1 = (elf1 + 1 + recipes[elf1]) % recipes.size
            elf2 = (elf2 + 1 + recipes[elf2]) % recipes.size
        }

        while(recipes.takeLast(count.toString().length).joinToString("") != count.toString()) {
            recipes.removeLast()
        }

        return recipes.size - count.toString().length
    }
}