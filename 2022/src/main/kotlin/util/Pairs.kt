package util

operator fun Pair<Int,Int>.plus(other: Pair<Int,Int>): Pair<Int,Int> {
    return (this.first + other.first) to (this.second + other.second)
}