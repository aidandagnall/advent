package days

class Day02 : Day(2) {
    override fun part1() : Any {
        var horiz = 0
        var depth = 0
        inputSplitList.forEach { (word, value) ->
            when (word) {
                "forward" -> horiz += value.toInt()
                "down"    -> depth += value.toInt()
                "up"      -> depth -= value.toInt()
            }
        }
        return horiz * depth
    }

    override fun part2() : Any {
        var horiz = 0
        var depth = 0
        var aim = 0
        inputSplitList.forEach { (word, value) ->
            when(word) {
                "forward" -> {
                    horiz += value.toInt()
                    depth += aim * value.toInt()
                }
                "down" -> aim += value.toInt()
                "up"   -> aim -= value.toInt()
            }
        }
        return horiz * depth
    }
}