package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {
    private val day = Day06()

    @Test
    fun testPartOne() {
        assertEquals(288, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(71503, day.part2())
    }
}