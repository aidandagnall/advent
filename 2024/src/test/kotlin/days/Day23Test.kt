package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day = Day23()

    @Test
    fun testPartOne() {
        assertEquals(7, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals("co,de,ka,ta", day.part2())
    }
}