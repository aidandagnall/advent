package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day = Day05()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), "CMZ")
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), "MCD")
    }
}