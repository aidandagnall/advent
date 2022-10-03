package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day = Day25()

    @Test
    fun testPartOne() {
        assertEquals(58, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals("Merry (belated) Christmas!", day.part2())
    }
}