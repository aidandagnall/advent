package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day = Day10()

    @Test
    fun testPartOne() {
        assertEquals(7, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(33, day.part2())
    }
}