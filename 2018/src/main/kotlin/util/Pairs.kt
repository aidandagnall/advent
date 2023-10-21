package util

import kotlin.math.abs

operator fun Pair<Int,Int>.plus(other: Pair<Int,Int>): Pair<Int,Int> {
    return (this.first + other.first) to (this.second + other.second)
}

fun Pair<Int,Int>.manhattan(other: Pair<Int,Int>): Int {
    return abs(this.first - other.first) + abs(this.second - other.second)
}

operator fun Pair<Int,Int>.times(scalar: Int) = first * scalar to second * scalar
