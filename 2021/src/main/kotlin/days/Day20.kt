package days

class Day20 : Day(20) {
    private val algo : List<Int> = inputList.first().map { if(it == '#') 1 else 0 }
    private val image = mutableSetOf<Pair<Int,Int>>()
    private fun getInputImage() {
        image.clear()
        (inputList.drop(2).forEachIndexed { x, line ->
            line.forEachIndexed { y, char ->
                if (char == '#') image.add(x to y)
            }
        })
    }

    override fun part1() : Any {
        getInputImage()
        (1..2).forEach { enhanceImage(it) }
        return image.count()
    }

    override fun part2() : Any {
        getInputImage()
        (1..50).forEach { enhanceImage(it) }
        return image.count()
    }

    private fun enhanceImage(iteration: Int) {
        val original = image.toSet()
        image.clear()
        val xRange = original.minOf { it.first  }..original.maxOf { it.first }
        val yRange = original.minOf { it.second  }..original.maxOf { it.second }
        (original.minOf { it.first  } - 1..original.maxOf { it.first } + 1).forEach { x ->
            (original.minOf { it.second } - 1..original.maxOf { it.second  } + 1).forEach { y ->
                val index = (x to y).neighbours()
                    .joinToString("") {
                        if (it in original) "1"
                        else if ((it.first !in xRange || it.second !in yRange) && iteration % 2 == 0) algo[0].toString()
                        else "0"
                    }.toInt(2)
                if (algo[index] == 1) image.add(x to y)
            }
        }
    }

    private fun printImage() {
        (image.minOf { it.second }..image.maxOf { it.second }).forEach { y ->
            (image.minOf { it.first }..image.maxOf { it.first }).forEach { x ->
                if (y to x in image) print("#")
                else print(".")
            }
            println()
        }
    }

    private fun Pair<Int,Int>.neighbours(): List<Pair<Int,Int>> {
        return (first - 1..first + 1).flatMap { x ->
            (second - 1..second + 1).map { y ->
                x to y
            }
        }
    }
}