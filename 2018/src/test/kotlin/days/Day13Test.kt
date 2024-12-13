package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day = Day13()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), "7,3")
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 0)
    }
}