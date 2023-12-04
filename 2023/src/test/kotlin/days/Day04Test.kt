package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04Test {
    private val day = Day04()

    @Test
    fun testPartOne() {
        assertEquals(13, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(30, day.part2())
    }
}