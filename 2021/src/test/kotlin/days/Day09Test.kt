package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun testPartOne() {
        assertEquals(15, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(1134, day.part2())
    }
}