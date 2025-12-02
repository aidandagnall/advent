package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day = Day02()

    @Test
    fun testPartOne() {
        assertEquals(1227775554L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}