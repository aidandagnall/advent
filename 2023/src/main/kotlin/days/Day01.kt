package days

class Day01 : Day(1) {
    override fun part1() : Any = inputList.sumOf { line ->
        line.filter { it.isDigit() }.let {
            (it.first().digitToInt()) * 10 + it.last().digitToInt()
        }
    }

    // this is without a doubt some of the worst code I have ever written
    override fun part2() : Any =inputList.sumOf { line ->
        line.replace("one", "one1one")
            .replace("two", "two2two")
            .replace("three", "three3three")
            .replace("four", "four4four")
            .replace("five", "five5five")
            .replace("six", "six6six")
            .replace("seven", "seven7seven")
            .replace("eight", "eight8eight")
            .replace("nine", "nine9nine")
            .filter { it.isDigit() }.let {
            it.first().digitToInt() * 10 + it.last().digitToInt()
        }
    }
}