package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day = Day21()

    @Test
    fun testPartOne() {
        assertEquals(126384L, day.part1())
    }
}