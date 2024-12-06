package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {
    private val day = Day06()

    @Test
    fun testPartOne() {
        assertEquals(0, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(6, day.part2())
    }
}