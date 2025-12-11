package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(5L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(2L, day.part2())
    }
}