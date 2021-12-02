package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day2Test {
    private val day = Day2()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 150)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 900)
    }
}