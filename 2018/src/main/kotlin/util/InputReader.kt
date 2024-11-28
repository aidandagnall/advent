package util

import java.io.File

object InputReader {
    fun getInputAsList(day : Int) : List<String> {
        val dayNum = if (day < 10) "0$day" else "$day"
        val uri = this::class.java.classLoader.getResource("day$dayNum.txt")?.toURI() ?:
            return listOf()
            return File(uri).readLines().onEach { it.trim() }
    }
}

fun <T> List<String>.parseGrid(f: (Char, Int, Int) -> T?): List<T> =
    flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            f(char, x, y)
        }.filterNotNull()
    }

fun List<String>.getPoints(predicate: (Char) -> Boolean): List<Pair<Int,Int>> =
    flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            if (predicate(char)) x to y else null
        }
    }.filterNotNull()