package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04Test {
    private val day = Day04()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 2)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 70)
    }
}