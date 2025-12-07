package days

import kotlin.math.max
import kotlin.math.min

class Day05 : Day(5) {
    val freshRanges = inputGroupedList.first().map {
        val (a, b) = it.split("-")
        a.toLong()..b.toLong()
    }

    val ingredients = inputGroupedList.last().map { it.toLong() }

    override fun part1() : Any = ingredients.count { ing -> freshRanges.any { ing in it } }
    override fun part2() : Any = freshRanges.simplify().sumOf { it.last - it.first + 1 }

    fun List<LongRange>.simplify(): List<LongRange> {
        val adjustedRanges = mutableSetOf<LongRange>()
        forEach { a ->
            forEach { b ->
                if (a == b) return@forEach
                if (b.first in a || a.first in b || a.last in b || b.last in a) {
                    adjustedRanges.add(min(a.first, b.first)..max(a.last, b.last))
                }
            }
        }
        return (adjustedRanges + this.filterNot { r -> adjustedRanges.any { r.first in it && r.last in it } })
            .let { if (it == this) this else it.toList().simplify() }
    }
}