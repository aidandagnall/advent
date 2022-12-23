package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day = Day23()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 110)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 20)
    }
}