package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 0)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), Triple(90,296,16))
    }
}