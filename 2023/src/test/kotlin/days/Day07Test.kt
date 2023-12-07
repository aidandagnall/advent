package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day = Day07()

    @Test
    fun testPartOne() {
        assertEquals(6440, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(5905, day.part2())
    }
}