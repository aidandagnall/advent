package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day = Day05()

    @Test
    fun testPartOne() {
        assertEquals(143, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(123, day.part2())
    }
}