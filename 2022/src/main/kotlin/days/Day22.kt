package days

class Day22 : Day(22) {

    enum class Direction { Right, Down, Left, Up }
    private fun Direction.turnLeft() : Direction = Direction.values()[(4 + ordinal - 1) % 4]
    private fun Direction.turnRight() : Direction = Direction.values()[(ordinal + 1) % 4]
    private val map = inputList.takeWhile { it != "" }.let { rows ->
        val width = rows.maxOf { it.length }
        rows.map { row ->
            (0 until width).map { cell ->
                row.getOrElse(cell) { ' ' }
            }
        }
    }

    // (row, column, facing)
    private val start = Triple(0, map.first().indexOfFirst { it == '.' }, Direction.Right)
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
        Direction.Right to (0 to 1),
        Direction.Down to (1 to 0),
        Direction.Left to (0 to -1),
        Direction.Up to (-1 to 0),
    )

    override fun part1(): Any {
        var pos = start

        instructions.forEach instruct@{ instruction ->
            when (instruction) {
                "L" -> pos = Triple(pos.first, pos.second, pos.third.turnLeft())
                "R" -> pos = Triple(pos.first, pos.second, pos.third.turnRight())
                else -> {
                    repeat(instruction.toInt()) {
                        val dir = facing[pos.third]!!
                        var newX = pos.first + dir.first
                        var newY = pos.second + dir.second

                        if ((map.getOrNull(newX)?.getOrNull(newY) ?: ' ') == ' ') {
                            when (pos.third) {
                                Direction.Right -> newY = map[newX].indexOfFirst { it != ' ' }
                                Direction.Down -> newX = map.indexOfFirst { it[newY] != ' ' }
                                Direction.Left -> newY = map[newX].indexOfLast { it != ' ' }
                                Direction.Up  -> newX = map.indexOfLast { it[newY] != ' ' }
                            }
                        }

                        if (map[newX][newY] == '#') {
                            return@instruct
                        }
                        pos = Triple(newX, newY, pos.third)
                    }
                }
            }
        }
        return (pos.first + 1) * 1000 + (pos.second + 1) * 4 + pos.third.ordinal
    }

    override fun part2(): Any {
        var pos = start

        instructions.forEach instruct@{ instruction ->
            when (instruction) {
                "L" -> pos = Triple(pos.first, pos.second, pos.third.turnLeft())
                "R" -> pos = Triple(pos.first, pos.second, pos.third.turnRight())
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
        return (pos.first + 1) * 1000 + (pos.second + 1) * 4 + pos.third.ordinal
    }

    private fun getRotatedCoordinates(pos: Triple<Int,Int,Direction>): Triple<Int,Int,Direction> {
        val dir = facing[pos.third]!!
        val x = pos.first + dir.first
        val y = pos.second + dir.second
        return when {
            //   AB
            //   C
            //  ED
            //  F

            // Left of A -> Left of E (going Right)
            pos.third == Direction.Left && y == 49 && x in 0 until 50 -> Triple(150 - (x % 50) - 1, 0, Direction.Right)
            // Up from A -> Left of F (going Right)
            pos.third == Direction.Up && x == -1 && y in 50 until 100 -> Triple(150 + (y % 50), 0, Direction.Right)

            // Right of B -> Right of D (going Left)
            pos.third == Direction.Right && y == 150 -> Triple(150 - (x % 50) - 1, 99, Direction.Left)
            // Down from B -> Right of C (going Left)
            pos.third == Direction.Down && x == 50 && y in 100 until 150 -> Triple(50 + ( y % 50), 100 - 1, Direction.Left)
            // Up from B -> Bottom of F (going Up)
            pos.third == Direction.Up && x == -1 && y in 100 until 150 -> Triple(199, (y % 50), Direction.Up)

            // Right of C -> Bottom of B (going Up)
            pos.third == Direction.Right && y == 100 && x in 50 until 100 -> Triple(49, 100 + (x % 50), Direction.Up)
            // Left of C -> Top of E (going Down)
            pos.third == Direction.Left && y == 49 && x in 50 until 100 -> Triple(100, (x % 50), Direction.Down)

            // Right of D -> Right of B (going Left)
            pos.third == Direction.Right && y == 100 && x in 100 until 150 -> Triple(50 - (x % 50) - 1, 149, Direction.Left)
            // Down from D -> Right of F (going Left)
            pos.third == Direction.Down && x == 150 && y in 50 until 100 -> Triple(150 + (y % 50), 49, Direction.Left)

            // Left of E -> Left of A (going Right)
            pos.third == Direction.Left && y == -1 && x in 100 until 150 -> Triple(50 - (x % 50) - 1, 50, Direction.Right)
            // Up from E -> Left of C (going Right)
            pos.third == Direction.Up && x == 99 && y in 0 until 50 -> Triple(50 + (y % 50), 50, Direction.Right)

            // Right of F -> Bottom of D (going Up)
            pos.third == Direction.Right && y == 50 && x in 150 until 200 -> Triple(149, 50 + (x % 50), Direction.Up)
            // Down from F -> Top of B (going Down)
            pos.third == Direction.Down && x == 200 -> Triple(0, 100 + (y % 50), Direction.Down)
            // Left of F -> Top of A (going Down)
            pos.third == Direction.Left && y == -1 && x in 150 until 200 -> Triple(0, 50 + (x % 50), Direction.Down)

            else -> Triple(x, y, pos.third)
        }
    }
}