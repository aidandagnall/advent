package days

import util.getPoints
import util.neighbours4
import util.parseGrid

class Day15 : Day(15) {
    enum class Type {
        GOBLIN,
        ELF
    }

    val walls = inputList.getPoints { it == '#' }.toSet()

    data class Unit(
        val position: Pair<Int,Int>,
        var hitPoints: Int = 200,
        val attackPower: Int = 3,
        val id: Int = 0,
        val type: Type
    )

    fun Unit.takeTurn(units: List<Unit>): List<Unit>  {
        val otherUnits = units.filter { it.id != id }
        return move(otherUnits)
            .attack(otherUnits)
    }

    fun Unit.move(units: List<Unit>): Unit {
        println("Moving $id")
        val targets = units.filter { it.type != type }
        val unitPositions = units.map { it.position }.toSet()
        val targetPositions = targets.map { it.position }.toSet()

//        println(targets)

        if (position.neighbours4().any { it in targetPositions }) {
//            println("  target in range")
            return this
        }

        val goal = targets.flatMap { it.position.neighbours4() }
            .filter { it !in walls }
            .filter { it !in unitPositions }
            .let {
//                println("          $it")
                it
            }
            .mapNotNull { bfs(position, it, targetPositions) }
            .sortedWith(compareBy<List<Pair<Int,Int>>> { it.size }.thenBy { it.last().second }.thenBy { it.last().first })
            .firstOrNull()?.lastOrNull()

        if (goal != null) {
//            println("  moving to $goal")
            return copy(position = goal)
        }
//        println("  not moving")
        return this
    }

    fun Unit.attack(units: List<Unit>): List<Unit> {
//        println("Attacking $id")
        val targetPositions = units.filter { it.type != type }.map { it.position }.toSet()
        val target = position.neighbours4()
            .filter { it in targetPositions }
            .sortedWith( compareBy<Pair<Int,Int>> { units.find { unit -> unit.position == it}!!.hitPoints}.thenBy { it.second }.thenBy { it.first })
            .firstOrNull()

        if (target == null) {
//            println("  No target")
            return units.filter { it.id != id } + this
        }

        val other = units.find { it.position == target }
            ?.let {
                it.copy(hitPoints = it.hitPoints - attackPower)
            } ?: error("UH OH")

        if (other.hitPoints < 0) {
//            println("  Killed target")
            return units.filter { it.id != other.id && it.id != id } + this
        }
//        println("  Hit target ${other.id}")
        return units.filter { it.id != other.id && it.id != id } + other + this
    }

    private fun bfs(start: Pair<Int,Int>, end: Pair<Int,Int>, otherUnitPositions: Set<Pair<Int,Int>>): List<Pair<Int,Int>>? {
//        println("    running bfs $start to $end")
        val queue = mutableListOf(start)
        val explored = mutableSetOf(start)
        val children = mutableMapOf(start to start)

        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
//            println("      v = $v")
            if (v == end) {
//                println("    success")

                var path = mutableListOf(v)
                var n = v
                while (n != start) {
//                    println("      n = $n")
                    if (children[n] == start) {
                        return path
                    }
                    n = children[n] ?: error("")
                    path.add(n)
                }
            }

            v.neighbours4().filter { it !in walls && it !in otherUnitPositions }.forEach {
//                println("      exploring $it")
                if (it !in explored) {
                    explored.add(it)
                    children[it] = v
                    queue.add(it)
                }
            }
        }

//        println("    failure")
        return null
    }

    val readingOrder: Comparator<Pair<Int,Int>> = compareBy<Pair<Int,Int>> { it.second }.thenBy { it.first }

    val initialUnits: List<Unit> = inputList.parseGrid<Unit> { char, x, y ->
        when (char) {
            'G' -> Unit(x to y, type = Type.GOBLIN)
            'E' -> Unit(x to y, type = Type.ELF)
            else -> null
        }
    }.mapIndexed { i, it ->
        it.copy(id = i)
    }

    override fun part1() : Any {
        println(walls)
        println(initialUnits)
        return generateSequence(initialUnits) { units ->
//            Thread.sleep(1000)
            val ids = units.map { it.id }

            val s = (0..50).joinToString("\n") { y ->
                (0..50).joinToString("") { x ->
                    when {
                        x to y in walls -> "#"
                        x to y in units.filter { it.type == Type.GOBLIN }.map { it.position } -> "G"
                        x to y in units.filter { it.type == Type.ELF}.map { it.position } -> "E"
                        else -> "."
                    }
                }
            }
            println(units)
            println(s)

            ids.fold(units) { acc, it ->
                val t = acc.find { unit -> unit.id == it }?.takeTurn(acc) ?: acc
//                println(t)
                t
            }.sortedWith(compareBy<Unit> { it.position.second }.thenBy { it.position.first })
        }.takeWhile {
            it.map { it.type }.toSet().size > 1
        }.let {
            it.count() * it.last().sumOf { it.hitPoints }
        }
    }

    override fun part2() : Any {
        return 0
    }
}
