package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 88)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 36)
    }
}