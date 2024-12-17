package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day = Day17()

    @Test
    fun testPartOne() {
        assertEquals(listOf(0, 1, 2), day.run(10, 0, 0, listOf(5, 0, 5, 1, 5, 4)))
        assertEquals(listOf(4,2,5,6,7,7,7,7,3,1,0), day.run(2024, 0, 0, listOf(0,1,5,4,3,0)))
    }


    @Test
    fun testPartTwo() {
        assertEquals(117440L, day.part2())
    }
}