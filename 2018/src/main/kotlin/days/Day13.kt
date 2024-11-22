package days

import util.takeWhileInclusive

class Day13 : Day(13) {

    data class Point(val x: Int, val y: Int)
    enum class Path {
        Intersection, Vertical, Horizontal, CurveRight, CurveLeft
    }
    enum class Direction {
        LEFT, DOWN, RIGHT, UP;
        fun left(): Direction = values()[(ordinal + 5) % 4]
        fun right(): Direction = values()[(ordinal + 3) % 4]
    }

    val map = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            Point(x, y) to when(char) {
                '+' -> Path.Intersection
                '-', '<', '>' -> Path.Horizontal
                '|', 'v', '^' -> Path.Vertical
                '/' -> Path.CurveRight
                '\\' -> Path.CurveLeft
                else -> return@mapIndexed null
            }
        }.filterNotNull()
    }.toMap()

    private val carts = inputList.flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            when (char) {
                '<' -> Cart(Point(x, y), Direction.LEFT, map)
                '>' -> Cart(Point(x, y), Direction.RIGHT, map)
                '^' -> Cart(Point(x, y), Direction.UP, map)
                'v' -> Cart(Point(x, y), Direction.DOWN, map)
                else -> null
            }
        }.filterNotNull()
    }

    data class Cart(val position: Point, val direction: Direction, val map: Map<Point, Path>, val nextIntersection: Direction = Direction.LEFT, var alive: Boolean = true, val id: Int = position.y * 10000 + position.x){
        override fun toString(): String = "{$position $direction ($id)}"
        fun move(): Cart {
            val newPos = when(direction) {
                Direction.LEFT -> Point(position.x - 1, position.y)
                Direction.RIGHT -> Point(position.x + 1, position.y)
                Direction.UP -> Point(position.x, position.y - 1)
                Direction.DOWN -> Point(position.x, position.y + 1)
            }

            if (newPos !in map) {
                error("$newPos outside of path")
            }

            return when(map[newPos]) {
                Path.CurveLeft -> when(direction) {
                        Direction.UP -> copy(position = newPos, direction = Direction.LEFT)
                        Direction.DOWN -> copy(position = newPos, direction = Direction.RIGHT)
                        Direction.LEFT -> copy(position = newPos, direction = Direction.UP)
                        Direction.RIGHT -> copy(position = newPos, direction = Direction.DOWN)
                    }
                Path.CurveRight -> when(direction) {
                    Direction.UP -> copy(position = newPos, direction = Direction.RIGHT)
                    Direction.DOWN -> copy(position = newPos, direction = Direction.LEFT)
                    Direction.LEFT -> copy(position = newPos, direction = Direction.DOWN)
                    Direction.RIGHT -> copy(position = newPos, direction = Direction.UP)
                }
                Path.Intersection -> when(nextIntersection) {
                    Direction.LEFT -> copy(position = newPos, direction = direction.left(), nextIntersection = Direction.UP)
                    Direction.UP -> copy(position = newPos, nextIntersection = Direction.RIGHT)
                    Direction.RIGHT -> copy(position = newPos, direction = direction.right(), nextIntersection = Direction.LEFT)
                    Direction.DOWN -> error("cannot have a nextIntersection of Down")
                }
                else -> copy(position = newPos)
            }
        }
    }


    override fun part1() : Any {
        return generateSequence(carts) { gen ->
            val positions = gen.map { it.position }
            gen.map {
                val next = it.move()
                if (next.position in positions) {
                    next.alive = false
                }
                next
            }
        }.takeWhileInclusive { gen ->
            gen.all { it.alive }
        }.last().first { !it.alive }.let { (pos, _) ->"${pos.x},${pos.y}" }
    }

    override fun part2() : Any {
        return generateSequence(carts) { gen ->
            gen.fold(gen) { acc, it->
                val next = it.move()
                val dropped = acc.drop(1)
                val collision = dropped.filter { it.alive }.find { it.position == next.position }
                if (next.alive && collision != null) {
                    collision.alive = false
                    dropped + next.copy(alive = false)
                } else {
                    dropped + next
                }
            }
        }.takeWhileInclusive { gen ->
            gen.filter { it.alive }.size > 1
        }.last().first { it.alive }.let { (pos, _) ->"${pos.x},${pos.y}" }
    }
}