package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day = Day02()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 15)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 12)
    }
}