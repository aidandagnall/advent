package days

import util.Direction
import util.parseGrid
import util.plus

class Day15 : Day(15) {

    private val mapInput = inputList.takeWhile { it != "" }
    private val instructionsInput = inputList.drop(mapInput.size + 1).takeWhile { it != "" }

    private val boxes = mapInput.parseGrid { pos, c -> if (c == 'O') pos else null }.associateWith { '#' }
    private val walls = mapInput.parseGrid { pos, c -> if (c == '#') pos else null }.toSet()
    private val robot = mapInput.parseGrid { pos, c -> if (c == '@') pos else null }.first()

    private val instructions = instructionsInput.joinToString("").map { it }

    override fun part1() : Any = run(robot, boxes, walls).second.calculateGPS()

    override fun part2() : Any {
        val scaledWalls = walls.flatMap { (x, y) -> listOf((2*x) to y, (2 * x) + 1 to y) }.toSet()
        val scaledBoxes = boxes.flatMap { (pos, _) ->
            val (x, y) = pos
            listOf((2*x to y) to '[', ((2 * x) + 1 to y) to ']')
        }.toMap()
        val scaledRobot = robot.first * 2 to robot.second

        return run(scaledRobot, scaledBoxes, scaledWalls).second.calculateGPS()
    }

    private fun Map<Pair<Int,Int>, Char>.calculateGPS() =
        filter { it.value == '#' || it.value == '['}.keys.sumOf { (x, y) -> (100 * y) + x }

    private fun run(robot: Pair<Int, Int>, boxes: Map<Pair<Int,Int>, Char>, walls: Set<Pair<Int, Int>>) =
        instructions.fold(robot to boxes) { (r, b), ins ->
            val dir = when(ins) {
                '>' -> Direction.EAST
                '^' -> Direction.NORTH
                'v' -> Direction.SOUTH
                '<' -> Direction.WEST
                else -> error("Invalid instruction '$ins'")
            }

            val boxes = b.toMutableMap()

            val boxesToMove = mutableMapOf<Pair<Int,Int>, Char>()
            val queue = mutableListOf(r + dir)
            val seen = mutableSetOf<Pair<Int,Int>>()
            while (queue.isNotEmpty()) {
                val v = queue.removeFirst()
                if (v in walls) return@fold r to b
                if (v !in b || v in seen) continue
                seen.add(v)

                when(b[v]) {
                    '#' -> Unit
                    '[' -> queue.add(v + Direction.EAST)
                    ']' -> queue.add(v + Direction.WEST)
                    else -> error("Unknown wall")
                }

                queue.add(v + dir)
                boxesToMove[v] = b[v]!!
            }

            boxesToMove.forEach {boxes.remove(it.key) }
            boxesToMove.forEach { boxes[it.key + dir] = it.value }

            r + dir to boxes
        }
}