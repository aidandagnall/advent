package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day = Day25()

    @Test
    fun testPartOne() {
        assertEquals(3, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals("Merry Christmas", day.part2())
    }
}