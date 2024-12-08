package util

import kotlin.math.abs

@JvmName("pairIntPlus")
operator fun Pair<Int,Int>.plus(other: Pair<Int,Int>): Pair<Int,Int> {
    return (this.first + other.first) to (this.second + other.second)
}

@JvmName("pairLongPlus")
operator fun Pair<Long,Long>.plus(other: Pair<Long,Long>): Pair<Long,Long> {
    return (this.first + other.first) to (this.second + other.second)
}

@JvmName("pairLongIntPlus")
operator fun Pair<Long,Long>.plus(other: Pair<Int,Int>): Pair<Long,Long> {
    return (this.first + other.first) to (this.second + other.second)
}

operator fun Pair<Int,Int>.plus(other: Direction): Pair<Int,Int> {
    return this + other.toVector()
}

enum class Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    fun toVector() = when(this) {
        NORTH -> 0 to -1
        NORTHEAST -> 1 to -1
        EAST -> 1 to 0
        SOUTHEAST -> 1 to 1
        SOUTH -> 0 to 1
        SOUTHWEST -> -1 to 1
        WEST -> -1 to 0
        NORTHWEST -> -1 to -1
    }
    fun turnRight() = when(this) {
        NORTH -> EAST
        NORTHEAST -> SOUTHEAST
        EAST -> SOUTH
        SOUTHEAST -> SOUTHWEST
        SOUTH -> WEST
        SOUTHWEST -> NORTHWEST
        WEST -> NORTH
        NORTHWEST -> NORTHEAST
    }
}

fun Pair<Int,Int>.manhattan(other: Pair<Int,Int>): Int {
    return abs(this.first - other.first) + abs(this.second - other.second)
}

fun Pair<Int,Int>.neighbours4(): List<Pair<Int,Int>> = listOf(
        first + 1 to second,
        first - 1 to second,
        first to second + 1,
        first to second - 1,
    )

fun Pair<Int,Int>.neighbours8(): List<Pair<Int,Int>> =
    (-1..1).flatMap { x ->
        (-1..1).map { y ->
            first + x to second + y
        }
    }.filter { it == this }

operator fun Pair<Int,Int>.times(scalar: Int) = first * scalar to second * scalar

fun Pair<Int,Int>.slopeTo(other: Pair<Int,Int>): Float? =
    if(first == other.first) null
    else (second.toFloat() - other.second.toFloat()) / (first.toFloat() - other.first.toFloat())
