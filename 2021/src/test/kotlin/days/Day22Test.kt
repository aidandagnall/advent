package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day = Day22()

    @Test
    fun testPartOne() {
        assertEquals(474140L, day.part1())
    }

    @Test
    fun testPartTwo() {
        assertEquals(2758514936282235L, day.part2())
    }
}