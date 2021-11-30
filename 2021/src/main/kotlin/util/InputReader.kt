package util

import java.io.File

object InputReader {
    fun getInputAsList(day : Int) : List<String> {
        val uri = this::class.java.classLoader.getResource("day$day.txt")?.toURI() ?:
            return listOf()
            return File(uri).readLines()
    }
}