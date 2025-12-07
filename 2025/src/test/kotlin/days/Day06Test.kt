package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {
    private val day = Day06()

    @Test
    fun testPartOne() {
        assertEquals(4277556L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(3263827L, day.part2())
    }
}