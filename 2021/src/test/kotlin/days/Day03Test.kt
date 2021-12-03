package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day = Day03()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 198)
        assertEquals(day.epsiolon(), 9)
        assertEquals(day.gamma(), 22)
    }


    @Test
    fun testPartTwo() {
//        assertEquals(day.part2(), 230)
        assertEquals(day.oxygen(), 23)
        assertEquals(day.co2(), 10)
    }
}