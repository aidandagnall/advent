package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day = Day14()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 24)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 93)
    }
}