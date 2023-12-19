package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day = Day19()

    @Test
    fun testPartOne() {
        assertEquals(19114, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(167409079868000L, day.part2())
    }
}