package days

class Day22 : Day(22) {

    private val map = inputList.takeWhile { it != "" }.let { rows ->
        val width = rows.maxOf { it.length }
        rows.map { row ->
            (0 until width).map { cell ->
                row.getOrElse(cell) { ' ' }
            }
        }
    }

    // (row, column, facing)
    private val start = Triple(0, map.first().indexOfFirst { it == '.' }, 0)
    private val instructions = inputList.last().let { line ->
        var l = line
        val ins = mutableListOf<String>()

        while (l.isNotEmpty()) {
            l = if (l[0].isDigit()) {
                ins.add(l.takeWhile { it.isDigit() })
                l.drop(ins.last().length)
            } else {
                ins.add(l[0].toString())
                l.drop(1)
            }
        }

        ins
    }

    private val facing = mapOf(
        0 to (0 to 1),
        1 to (1 to 0),
        2 to (0 to -1),
        3 to (-1 to 0),
    )

    override fun part1(): Any {
        var pos = start

        instructions.forEach instruct@{ instruction ->
            when (instruction) {
                "L" -> pos = Triple(pos.first, pos.second, (4 + pos.third - 1) % 4)
                "R" -> pos = Triple(pos.first, pos.second, (pos.third + 1) % 4)
                else -> {
                    val movement = instruction.toInt()
                    (1..movement).forEach move@{ step ->
                        val dir = facing[pos.third]!!
                        var newX = pos.first + dir.first
                        var newY = pos.second + dir.second

                        if ((map.getOrNull(newX)?.getOrNull(newY) ?: ' ') == ' ') {
                            when (pos.third) {
                                0 -> newY = map[newX].indexOfFirst { it != ' ' }
                                1 -> newX = map.indexOfFirst { it[newY] != ' ' }
                                2 -> newY = map[newX].indexOfLast { it != ' ' }
                                3 -> newX = map.indexOfLast { it[newY] != ' ' }
                            }
                        }

                        if (map[newX][newY] == '#') {
                            println("  found wall, stopping '${map[newX][newY]}'")
                            return@instruct
                        }
                        pos = Triple(newX, newY, pos.third)
                    }
                }
            }
        }
        return (pos.first + 1) * 1000 + (pos.second + 1) * 4 + pos.third
    }

    override fun part2(): Any {
        var pos = start

        instructions.forEach instruct@{ instruction ->
            println(pos)
            println("doing instruction $instruction")
            when (instruction) {
                "L" -> pos = Triple(pos.first, pos.second, (4 + pos.third - 1) % 4)
                "R" -> pos = Triple(pos.first, pos.second, (pos.third + 1) % 4)
                else -> {
                    val movement = instruction.toInt()
                    (1..movement).forEach move@{ _ ->
                        val (newX, newY, newDir) = getRotatedCoordinates(pos)

                        if (map[newX][newY] == '#') return@instruct
                        pos = Triple(newX, newY, newDir)
                    }
                }
            }
        }
        return (pos.first + 1) * 1000 + (pos.second + 1) * 4 + pos.third
    }

    private fun getRotatedCoordinates(pos: Triple<Int,Int,Int>): Triple<Int,Int,Int> {
        val dir = facing[pos.third]!!
        var newX = pos.first + dir.first
        var newY = pos.second + dir.second
        var newDir = pos.third
        when {
            pos.third == 2 && newY == 49 && newX in 0 until 50 -> {
                newX = 150 - (newX % 50) - 1
                newY = 0
                newDir = 0
            }

            pos.third == 3 && newX == -1 && newY in 50 until 100 -> {
                newX = 150 + (newY % 50)
                newY = 0
                newDir = 0
            }


            pos.third == 0 && newY == 150 -> {
                newX = 150 - (newX % 50) - 1
                newY = 99
                newDir = 2
            }

            pos.third == 1 && newX == 50 && newY in 100 until 150 -> {
                newX = 50 + ( newY % 50)
                newY = 100 - 1
                newDir = 2
            }

            pos.third == 3 && newX == -1 && newY in 100 until 150 -> {
                newX = 199
                newY = (newY % 50)
                newDir = 3
            }

            pos.third == 0 && newY == 100 && newX in 50 until 100 -> {
                newY = 100 + newX % 50
                newX = 49
                newDir = 3
            }

            pos.third == 2 && newY == 49 && newX in 50 until 100 -> {
                newY = (newX % 50)
                newX = 100
                newDir = 1
            }

            pos.third == 0 && newY == 100 && newX in 100 until 150 -> {
                newY = 149
                newX = 50 - (newX % 50) - 1
                newDir = 2
            }

            pos.third == 1 && newX == 150 && newY in 50 until 100 -> {
                newX = 150 + (newY % 50)
                newY = 49
                newDir = 2
            }

            pos.third == 2 && newY == -1 && newX in 100 until 150 -> {
                newX = 50 - (newX % 50) - 1
                newY = 50
                newDir = 0
            }

            pos.third == 3 && newX == 99 && newY in 0 until 50 -> {
                newX = 50 + (newY % 50)
                newY = 50
                newDir = 0
            }

            pos.third == 0 && newY == 50 && newX in 150 until 200 -> {
                newY = 50 + (newX % 50)
                newX = 149
                newDir = 3
            }

            pos.third == 1 && newX == 200 -> {
                newY = 100 + (newY % 50)
                newX = 0
                newDir = 1
            }

            pos.third == 2 && newY == -1 && newX in 150 until 200 -> {
                newY = 50 + (newX % 50)
                newX = 0
                newDir = 1
            }
        }

        return Triple(newX, newY, newDir)
    }
}