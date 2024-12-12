package util

fun <T> Set<T>.powerset() : Set<Set<T>> =
    if (isEmpty()) setOf(emptySet())
    else {
        val powersetOfRest = this.drop(1).toSet().powerset()
        powersetOfRest + powersetOfRest.map { it + this.first() }
    }

fun Set<Pair<Int,Int>>.scale(scalar: Int) = flatMap { p ->
    (0..<scalar).flatMap { x -> (0..< scalar).map { y -> (p * scalar) + (x to y)  } }
}.toSet()
