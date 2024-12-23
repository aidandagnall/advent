package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(55312L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(65601038650482L, day.part2())
    }
}