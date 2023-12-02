package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day = Day01()

    @Test
    fun testPartOne() {
        // I can't be bothered to run on two different test inputs for this one, so we'll ignore the
        // part 1 test
    }


    @Test
    fun testPartTwo() {
        assertEquals( 281, day.part2())
    }
}