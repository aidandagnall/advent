package util

inline fun <reified T> transpose(xs: List<List<T>>): List<List<T>> {
    return List(xs[0].size) { j ->
        List(xs.size) { i ->
            xs[i][j]
        }
    }
}

