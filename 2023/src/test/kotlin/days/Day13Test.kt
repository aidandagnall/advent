package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day = Day13()

    @Test
    fun testPartOne() {
        assertEquals(405, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}