package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day = Day03()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 198)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 230)
    }
}