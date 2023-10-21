package util

inline fun <reified T> transpose(xs: List<List<T>>): List<List<T>> {
    return List(xs[0].size) { j ->
        List(xs.size) { i ->
            xs[i][j]
        }
    }
}

fun List<Int>.product(): Int {
    return this.fold(1) {acc, i -> acc * i}
}
fun List<Long>.product(): Long {
    return this.fold(1L) {acc, i -> acc * i}
}
