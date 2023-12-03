package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day = Day03()

    @Test
    fun testPartOne() {
        assertEquals(4361, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}