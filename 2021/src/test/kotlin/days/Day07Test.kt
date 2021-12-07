package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day = Day07()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 37)
    }


    @Test
    fun testPartTwo() {
        assertEquals(168, day.part2())
    }
}