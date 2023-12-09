package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun testPartOne() {
        assertEquals(114, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(2, day.part2())
    }
}