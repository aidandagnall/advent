package days

class Day02 : Day(2) {
    override fun part1() : Any {
        var horiz = 0
        var depth = 0
        inputSplitList.forEach {
            when (it[0]) {
                "forward" -> horiz += it[1].toInt()
                "down"    -> depth += it[1].toInt()
                "up"      -> depth -= it[1].toInt()
            }
        }
        return horiz * depth
    }

    override fun part2() : Any {
        var horiz = 0
        var depth = 0
        var aim = 0
        inputSplitList.forEach {
            when(it[0]) {
                "forward" -> {
                    horiz += it[1].toInt()
                    depth += aim * it[1].toInt()
                }
                "down" -> aim += it[1].toInt()
                "up"   -> aim -= it[1].toInt()
            }
        }
        return horiz * depth
    }
}