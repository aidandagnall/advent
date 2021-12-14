package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day = Day14()

    @Test
    fun testPartOne() {
        assertEquals(1588L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(2188189693529L, day.part2())
    }
}