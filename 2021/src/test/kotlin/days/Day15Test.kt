package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day = Day15()

    @Test
    fun testPartOne() {
        assertEquals(40, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(315, day.part2())
    }
}