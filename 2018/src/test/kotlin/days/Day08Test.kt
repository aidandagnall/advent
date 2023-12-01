package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day = Day08()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 138)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 66)
    }
}