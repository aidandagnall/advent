package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day = Day14()

    @Test
    fun testPartOne() {
        assertEquals(136L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(64L, day.part2())
    }
}