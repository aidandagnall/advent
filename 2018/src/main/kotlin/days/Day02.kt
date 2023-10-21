package days

class Day02 : Day(2) {
    override fun part1() : Any {
        var count2 = 0
        var count3 = 0
        inputList.forEach {
            val count = it.groupingBy { it }.eachCount()

            if (count.any { it.value == 2}) {
                count2++
            }

            if (count.any { it.value == 3}) {
                count3++
            }
        }

        return count2 * count3
    }

    override fun part2() : Any {
        inputList.forEach { first ->
            
            inputList.forEach { second ->
                
                var diff = 0
                first.forEachIndexed { index, c ->
                    if (c != second[index]) diff++
                }
            }
        }
        return 0
    }
}