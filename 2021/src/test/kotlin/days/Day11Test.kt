package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(1656, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(195, day.part2())
    }
}