package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day = Day24()

    @Test
    fun testPartOne() {
        assertEquals(2, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}