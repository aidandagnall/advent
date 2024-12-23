package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day = Day17()

    @Test
    fun testPartOne() {
        assertEquals("5,7,3,0", day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(117440L, day.part2())
    }
}