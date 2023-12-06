package days

class Day06 : Day(6) {

    private val races = inputList.map { line ->
        "(\\d+)".toRegex().findAll(line).map { it.value.toLong() }
    }.let { (times, dists) -> times.zip(dists) }.toList()

    override fun part1() : Any = races.map {  (time, dist) -> wins(time, dist) }
        .reduce { acc, it -> acc * it}

    override fun part2() : Any = races.unzip().let { (times, dists) ->
            times.joinToString("").toLong() to dists.joinToString("").toLong()
        }.let { (time, dist) -> wins(time, dist) }

    private fun wins(time: Long, dist: Long): Int = (0..time).count { speed ->
            (time - speed) * speed > dist
        }
}