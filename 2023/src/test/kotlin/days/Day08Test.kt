package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day = Day08()

    @Test
    fun testPartOne() {
        assertEquals(2, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(2L, day.part2())
    }
}