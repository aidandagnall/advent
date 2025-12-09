package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun testPartOne() {
        assertEquals(50L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(24L, day.part2())
    }
}