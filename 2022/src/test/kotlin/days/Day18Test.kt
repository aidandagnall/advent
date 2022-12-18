package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18Test {
    private val day = Day18()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 64)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 58)
    }
}