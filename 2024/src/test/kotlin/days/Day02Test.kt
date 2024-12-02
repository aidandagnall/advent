package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day = Day02()

    @Test
    fun testPartOne() {
        assertEquals(2, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(4, day.part2())
    }
}