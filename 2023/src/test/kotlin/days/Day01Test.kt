package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day = Day01()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 142)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 281)
    }
}