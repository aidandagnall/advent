package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day = Day21()

    @Test
    fun testPartOne() {
        assertEquals(739785, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals(444356092776315, day.part2())
    }
}