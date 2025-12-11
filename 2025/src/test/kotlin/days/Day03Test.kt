package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day = Day03()

    @Test
    fun testPartOne() {
        assertEquals(357L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(3121910778619L, day.part2())
    }
}