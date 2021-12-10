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