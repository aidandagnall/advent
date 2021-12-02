package days

class Day01 : Day(1) {
    override fun part1() : Any {
        return inputIntList.filterIndexed { i, it -> i > 0 && it > inputIntList[i - 1] }.count()
    }

    override fun part2() : Any {
        return inputIntList.filterIndexed { i, _ ->
            i in 1..inputIntList.size - 3 && threeDaySum(i) > threeDaySum(i - 1)
        }.count()
    }

    private fun threeDaySum(i : Int) : Int{
        return inputIntList.slice(i until i+3).sum()
    }
}