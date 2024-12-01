package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day = Day01()

    @Test
    fun testPartOne() {
        assertEquals(11, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(31, day.part2())
    }
}