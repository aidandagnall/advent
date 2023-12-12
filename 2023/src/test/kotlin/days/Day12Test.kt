package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day = Day12()

    @Test
    fun testPartOne() {
        assertEquals(21, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(525152, day.part2())
    }
}