package util

fun <T> Set<T>.powerset() : Set<Set<T>> =
    if (isEmpty()) setOf(emptySet())
    else {
        val powersetOfRest = this.drop(1).toSet().powerset()
        powersetOfRest + powersetOfRest.map { it + this.first() }
    }
