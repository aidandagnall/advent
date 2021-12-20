package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18Test {
    private val day = Day18()

    @Test
    fun testPartOne() {
        assertEquals(4140, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals(3993, day.part2())
    }
}