package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    private val day = Day07()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 95437)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 24933642)
    }
}