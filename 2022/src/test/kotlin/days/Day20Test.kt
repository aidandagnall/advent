package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day = Day20()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 3)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 1623178306L)
    }
}