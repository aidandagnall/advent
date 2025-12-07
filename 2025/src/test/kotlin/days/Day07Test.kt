package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day = Day07()

    @Test
    fun testPartOne() {
        assertEquals(21L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(40L, day.part2())
    }
}