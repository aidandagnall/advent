package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day = Day07()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), "CABDFE")
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.run(2, 0).second, 15)
    }
}