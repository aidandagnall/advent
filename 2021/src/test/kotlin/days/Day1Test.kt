package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day1Test {
    private val day = Day1()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 7)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 5)
    }
}