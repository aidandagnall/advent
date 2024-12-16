package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day = Day15()

    @Test
    fun testPartOne() {
        assertEquals(10092, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(9021, day.part2())
    }
}