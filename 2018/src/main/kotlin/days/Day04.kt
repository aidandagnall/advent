package days

class Day04 : Day(4) {

    private val regex = """\[.*:(\d+)] (.*)""".toRegex()

    private val sleep = mutableMapOf<Int, MutableList<Int>>()

    init {
        var guard: Int = -1
        var fellAsleep: Int = -1
        inputList.sortedBy { line -> line.takeWhile { it != ']' } }.map {
            regex.matchEntire(it)!!.groupValues.let {
                when {
                    it[2].contains("Guard") -> {
                        guard = it[2].split(" ")[1].drop(1).toInt()

                        if (guard !in sleep) {
                            sleep[guard] = MutableList(60) { 0 }
                        }
                    }
                    it[2].contains("falls asleep") -> {
                        fellAsleep = it[1].toInt()
                    }
                    it[2].contains("wakes up") -> {
                        if (fellAsleep != -1) {
                            (fellAsleep until it[1].toInt()).forEach { minute ->
                                sleep[guard]!![minute]++
                            }
                        }
                    }
                }
            }
        }
    }

    override fun part1(): Any {
        return sleep.maxBy { (_, v) -> v.sum() }.let { (key, value) ->
            key * value.indices.maxBy { value[it] }
        }
    }

    override fun part2(): Any {
        return sleep.maxBy { (_, v) -> v.max() }.let { (key, value) ->
            key * value.indices.maxBy { value[it] }
        }
    }
}
