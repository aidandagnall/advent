package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day = Day19()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 33)
    }


    @Test
    fun testPartTwo() {
        // ignore, as cache is too large for JUnit test
    }
}