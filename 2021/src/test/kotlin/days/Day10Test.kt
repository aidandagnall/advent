package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day = Day10()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 26397)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 288957)
    }
}