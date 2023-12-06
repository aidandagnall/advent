package days

class Day06 : Day(6) {

    private val times = "(\\d+)".toRegex().findAll(inputList.first()).map { it.value.toInt() }
    private val dists = "(\\d+)".toRegex().findAll(inputList.last()).map { it.value.toInt() }
    private val races = times.zip(dists).toList()

    override fun part1() : Any = races.map {  (time, dist) ->
            (0..time).count { speed ->
                (time - speed) * speed > dist
            }
        }.fold(1) { acc, it -> acc * it}

    override fun part2() : Any {
        val sumTime = inputList.first().removePrefix("Time:").replace(" ", "").toLong()
        val sumDist = inputList.last().removePrefix("Distance:").replace(" ", "").toLong()
        return (0..sumTime).count { speed ->
            (sumTime - speed) * speed > sumDist
        }
    }
}