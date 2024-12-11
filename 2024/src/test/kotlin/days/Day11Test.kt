package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(55312, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}