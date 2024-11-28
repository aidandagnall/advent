package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day = Day15()

    @Test
    fun testPartOne() {
        assertEquals(18740, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 0)
    }
}