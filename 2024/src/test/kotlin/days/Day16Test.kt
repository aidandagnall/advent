package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day = Day16()

    @Test
    fun testPartOne() {
        assertEquals(11048, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(64, day.part2())
    }
}