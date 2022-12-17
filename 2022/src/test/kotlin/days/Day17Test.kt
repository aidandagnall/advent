package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day = Day17()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 3068)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 1514285714288L)
    }
}