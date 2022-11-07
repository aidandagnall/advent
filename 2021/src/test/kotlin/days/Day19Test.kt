package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day = Day19()

    @Test
    fun testPartOne() {
        assertEquals(79, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals(3621, day.part2())
    }
}