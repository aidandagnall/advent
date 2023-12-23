package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day = Day23()

    @Test
    fun testPartOne() {
        assertEquals(94, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(154, day.part2())
    }
}