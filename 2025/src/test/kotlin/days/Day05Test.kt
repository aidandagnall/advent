package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day = Day05()

    @Test
    fun testPartOne() {
        assertEquals(3, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(14L, day.part2())
    }
}