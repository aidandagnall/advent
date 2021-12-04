package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04Test {
    private val day = Day04()

    @Test
    fun testPartOne() {
        assertEquals(4512, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(1924, day.part2())
    }
}