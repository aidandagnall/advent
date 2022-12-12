package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day = Day12()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 31)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 29)
    }
}