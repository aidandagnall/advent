package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day = Day22()

    @Test
    fun testPartOne() {
        assertEquals(5, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(7, day.part2())
    }
}