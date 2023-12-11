package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun testPartOne() {
        assertEquals(374L, day.solveImage(2))
    }


    @Test
    fun testPartTwo() {
        assertEquals(8410L, day.solveImage(100))
    }
}