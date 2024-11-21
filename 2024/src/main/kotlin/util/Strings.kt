package util

fun String.separate(delimiter: Char): List<String> = split(delimiter).filterNotEmpty()
