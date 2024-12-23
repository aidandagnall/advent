package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day = Day22()

    @Test
    fun testPartOne() {
        assertEquals(37327623L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(24, day.part2())
    }
}