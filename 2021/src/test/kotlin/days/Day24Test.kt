package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day = Day24()

    @Test
    fun testPartOne() {
        assertEquals(29991993698469L, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals(14691271141118L, day.part2())
    }
}