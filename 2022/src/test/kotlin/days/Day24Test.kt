package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day = Day24()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 18)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 54)
    }
}