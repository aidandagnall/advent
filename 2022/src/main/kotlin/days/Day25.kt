package days

import kotlin.math.pow

class Day25 : Day(25) {
    override fun part1() : Any = inputList.sumOf { it.parseSNAFU() }.toSNAFU()
    override fun part2() : Any = "Merry Christmas"

    private fun String.parseSNAFU(): Long {
        var power = this.length - 1
        return this.sumOf {
            when(it) {
                '0', '1', '2' -> (it - '0').toDouble()
                '-' -> -1.0
                '=' -> -2.0
                else -> 0.0
            } * 5.0.pow(power--)
        }.toLong()
    }

    private fun Long.toSNAFU(): String {
        var a = this
        var out = ""
        while (a > 0) {
            out = "=-012"[((a + 2) % 5).toInt()] + out
            a = (a + 2) / 5
        }
        return out
    }
}