package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(374, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(8410, day.part2())
    }
}