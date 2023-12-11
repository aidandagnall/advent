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
