package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day = Day02()

    @Test
    fun testPartOne() {
        assertEquals(8, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals( 2286, day.part2())
    }
}