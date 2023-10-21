package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day = Day05()

    @Test
    fun testPartOne() {
        assertEquals(10, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(4, day.part2())
    }
}