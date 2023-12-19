package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18Test {
    private val day = Day18()

    @Test
    fun testPartOne() {
        assertEquals(62L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(952408144115, day.part2())
    }
}