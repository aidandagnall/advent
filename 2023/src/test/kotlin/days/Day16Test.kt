package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day = Day16()

    @Test
    fun testPartOne() {
        assertEquals(46, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}