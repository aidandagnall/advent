package util

inline fun <reified T> List<List<T>>.transpose(): List<List<T>> {
    return List(this[0].size) { j ->
        List(this.size) { i ->
            this[i][j]
        }
    }
}

fun List<Int>.product(): Int {
    return this.fold(1) {acc, i -> acc * i}
}
fun List<Long>.product(): Long {
    return this.fold(1L) {acc, i -> acc * i}
}

fun <T> List<T>.replace(old: T, new: T): List<T> = map { if (it == old) new else it }

fun <T> List<T>.replaceAt(index: Int, new: T): List<T> = take(index) + listOf(new) + drop(index + 1)

fun <T> List<T>.repeat(n: Int): List<T> = (1..< n).fold(this) { acc, _ ->
    acc + this
}

fun List<String>.filterNotEmpty(): List<String> = this.filter { it.isNotEmpty() }

fun List<String>.getPoints() = List(size) { y ->
    List(first().count()) { x ->
        x to y
    }
}.flatten()

fun <T> List<String>.parseGrid(f: (Pair<Int,Int>, Char) -> T?): List<T> =
    flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            f(x to y, c)
        }
    }.filterNotNull()

fun <T> Collection<T>.getAllPairs() = flatMap { a ->
    mapNotNull { b -> if (a == b) null else a to b}
}

infix fun <T> T.inAny(collection : Collection<Collection<T>>) = collection.any { this in it }

fun <T> List<T>.split(f: (T) -> Boolean): List<List<T>> {
    var input = this
    val output = mutableListOf<List<T>>()
    while(input.isNotEmpty()) {
        val group = input.takeWhile { !f(it) }
        output.add(group)
        input = input.drop(group.size + 1)
    }
    return output.toList()
}
