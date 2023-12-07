package days

import util.replace

class Day07 : Day(7) {
    data class Hand(val cards: List<Char>, val bid: Int): Comparable<Hand> {
        private val handValue = handType() + cards.map {
            when(it) {
                'A' -> 'E'
                'K' -> 'D'
                'Q' -> 'C'
                'J' -> 'B'
                'T' -> 'A'
                '*' -> '1'
                else -> it
            }
        }.joinToString("")

        override fun compareTo(other: Hand): Int = handValue.compareTo(other.handValue)

        private fun handType(): String = cards.groupingBy { it }.eachCount()
            .map { (char, count) -> if (char == '*') 0 else count }
            .sortedDescending()
            .let { listOf(it.first() + cards.count { it == '*' }) + it.drop(1)  }
            .joinToString("")
            .take(2)
            .padEnd(2, '0')
    }

    private val hands = inputList.map {
        it.split(" ").let { (cards, bid) -> Hand(cards.toList(), bid.toInt())}
    }

    override fun part1() : Any = hands.sorted().betSum()

    override fun part2() : Any = hands
            .map { (cards, bid) -> Hand(cards.replace('J', '*'), bid) }
            .sorted()
            .betSum()

    private fun List<Hand>.betSum(): Int = foldIndexed(0) { i, acc, hand ->
        acc + ((i + 1) * hand.bid)
    }
}