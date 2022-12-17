package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day = Day15()

    @Test
    fun testPartOne() {
        assertEquals(day.getClearPositionsInRow(10), 26)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.getMissingSensorFreq(20), 56_000_011L)
    }
}