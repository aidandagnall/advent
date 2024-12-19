package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day = Day19()

    @Test
    fun testPartOne() {
        assertEquals(6, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(16L, day.part2())
    }
}