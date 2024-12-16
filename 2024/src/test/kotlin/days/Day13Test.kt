package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day = Day13()

    @Test
    fun testPartOne() {
        assertEquals(480L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(875318608908L, day.part2())
    }
}