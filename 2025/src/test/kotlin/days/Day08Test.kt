package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day = Day08()

    @Test
    fun testPartOne() {
        assertEquals(19, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(25272L, day.part2())
    }
}