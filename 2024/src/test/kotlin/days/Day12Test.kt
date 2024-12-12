package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day = Day12()

    @Test
    fun testPartOne() {
        assertEquals(1930, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(1206, day.part2())
    }
}