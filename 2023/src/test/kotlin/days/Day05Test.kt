package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {
    private val day = Day05()

    @Test
    fun testPartOne() {
        assertEquals(35L, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(46L, day.part2())
    }
}