package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day = Day13()

    @Test
    fun testPartOne() {
        assertEquals(17, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals("""
            █████
            █    █ 
            █    █ 
            █    █ 
            █████
            """.trimIndent(), day.part2())
    }
}