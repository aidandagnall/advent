package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun testPartOne() {
        assertEquals(1928L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(2858L, day.part2())
    }
}