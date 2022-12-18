package days

class Day18 : Day(18) {

    private val cubes = inputList.map { it.split(",").let { (x, y, z) ->
        Triple(x.toInt(), y.toInt(), z.toInt()) }
    }.toSet()
    private val steam = mutableSetOf<Triple<Int,Int,Int>>()
    private val xMax = cubes.maxOf { it.first } + 1
    private val yMax = cubes.maxOf { it.second } + 1
    private val zMax = cubes.maxOf { it.third } + 1

    override fun part1() : Any {
        return cubes.sumOf {
            6 - it.getFaces().count{ it in cubes }
        }
    }

    override fun part2() : Any {
        fill(Triple(0,0,0))
        return cubes.sumOf {
            it.getFaces().count { it in steam }
        }
    }

    private fun fill(coord: Triple<Int,Int,Int>) {
        if (coord in cubes) return
        if (coord in steam) return

        val (x, y, z) = coord
        if (x !in -1..xMax || y !in -1..yMax || z !in -1..zMax) return

        steam.add(coord)
        coord.getFaces().forEach { fill(it) }
    }

    private fun Triple<Int,Int,Int>.getFaces(): List<Triple<Int,Int,Int>> {
        return listOf(
            Triple(this.first - 1, this.second, this.third),
            Triple(this.first + 1, this.second, this.third),
            Triple(this.first, this.second - 1, this.third),
            Triple(this.first, this.second + 1, this.third),
            Triple(this.first, this.second, this.third - 1),
            Triple(this.first, this.second, this.third + 1),
        )
    }
}