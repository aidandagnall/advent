package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day = Day17()

    @Test
    fun testPartOne() {
        assertEquals(102, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(94, day.part2())
    }
}