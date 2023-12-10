package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day = Day10()

    @Test
    fun testPartOne() {
        assertEquals(70, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(8, day.part2())
    }
}