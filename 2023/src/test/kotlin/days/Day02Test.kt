package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day = Day02()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 8)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 2286)
    }
}