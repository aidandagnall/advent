package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {
    private val day = Day06()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 5934L)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 26984457539)
    }
}