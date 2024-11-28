package util

import kotlin.math.abs

operator fun Pair<Int,Int>.plus(other: Pair<Int,Int>): Pair<Int,Int> {
    return (this.first + other.first) to (this.second + other.second)
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
