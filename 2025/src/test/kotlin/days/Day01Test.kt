package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day = Day01()

    @Test
    fun testPartOne() {
        assertEquals(3, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(6, day.part2())
    }
}