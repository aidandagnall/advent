package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day = Day16()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 1651)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 1707)
    }
}