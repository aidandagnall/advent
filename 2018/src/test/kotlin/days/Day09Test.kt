package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun testPartOne() {
        assertEquals(32, day.run(9, 25))
//        assertEquals(8317, day.run(10, 1618))
//        assertEquals(146373, day.run(13, 7999))
//        assertEquals(2764, day.run(17, 1104))
//        assertEquals(54718, day.run(21, 6111))
//        assertEquals(37305, day.run(30, 5807))
    }

    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 0)
    }
}