package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day = Day21()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 152L)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 301L)
    }
}