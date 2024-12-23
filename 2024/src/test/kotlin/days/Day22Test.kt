package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day = Day22()

    @Test
    fun testPartOne() {
        assertEquals(0, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(23L, day.part2())
    }
}