package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 10605L)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 2713310158L)
    }
}