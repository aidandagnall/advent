package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day = Day25()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 0)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), "Merry Christmas")
    }
}