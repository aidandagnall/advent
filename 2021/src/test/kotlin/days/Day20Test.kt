package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day = Day20()

    @Test
    fun testPartOne() {
        assertEquals(35, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals(3351, day.part2())
    }
}