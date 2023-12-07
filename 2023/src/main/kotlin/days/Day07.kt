package days

class Day07 : Day(7) {
    data class Hand(val cards: List<Char>, val bid: Int): Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            return cards.zip(other.cards).map { (a, b) ->
                cardValues[a]!!.compareTo(cardValues[b]!!)
            }.first { it != 0 }
        }
        companion object {
            val cardValues = mapOf(
                'A' to 14,
                'K' to 13,
                'Q' to 12,
                'T' to 10,
                '9' to 9,
                '8' to 8,
                '7' to 7,
                '6' to 6,
                '5' to 5,
                '4' to 4,
                '3' to 3,
                '2' to 2,
                'J' to 1,
            )
        }
    }

    val hands = inputList.map {
        it.split(" ").let { (cards, bid) -> Hand(cards.toList(), bid.toInt())}
    }

    override fun part1() : Any {
        return hands.groupBy {  hand ->
            when {
                hand.cards.all { it == hand.cards.first()} -> 7
                hand.cards.groupingBy { it }.eachCount().containsValue(4) -> 6
                hand.cards.groupingBy { it }.eachCount().let { it.containsValue(3) && it.containsValue(2) } -> 5
                hand.cards.groupingBy { it }.eachCount().containsValue(3) -> 4
                hand.cards.groupingBy { it }.eachCount().values.count { it == 2 } == 2 -> 3
                hand.cards.groupingBy { it }.eachCount().containsValue(2) -> 2
                else -> 1
            }
        }.toList().sortedBy { it.first }.flatMap {  (value, hands) ->
            println("$value has ${hands.count()} -> $hands")
            hands.sortedBy { it }
        }.also {
            it.forEach(::println)
        }
            .foldIndexed(0) { index, acc, hand ->
                println("$hand gets ${(index + 1) * hand.bid}")
            acc + ((index + 1) * hand.bid)
        }
    }

    fun handValue(cards: List<Char>): Int {
        return when {
            cards.all { it == cards.first() } -> 7
            cards.groupingBy { it }.eachCount().containsValue(4) -> 6
            cards.groupingBy { it }.eachCount()
                .let { it.containsValue(3) && it.containsValue(2) } -> 5

            cards.groupingBy { it }.eachCount().containsValue(3) -> 4
            cards.groupingBy { it }.eachCount().values.count { it == 2 } == 2 -> 3
            cards.groupingBy { it }.eachCount().containsValue(2) -> 2
            else -> 1
        }
    }

    override fun part2() : Any {
        return hands.groupBy {  hand ->
            if ('J' !in hand.cards)
                return@groupBy handValue(hand.cards)

            val count = hand.cards.count { it == 'J' }
            val dests = Hand.cardValues.keys.filter { it != 'J' }

            val options = mutableListOf<List<Char>>(hand.cards)

            (1..count).forEach {
                val remaining = options
                options.addAll(
                    remaining.flatMap { hand ->
                        dests.map { card -> hand.indexOf('J').let { index -> hand.take(index) + card + hand.drop(index + 1) }}
                    }
                )
                println(options)
            }

            options.filter { 'J' !in it }.maxOf { handValue(it) }
        }.toList().sortedBy { it.first }.flatMap {  (value, hands) ->
            println("$value has ${hands.count()} -> $hands")
            hands.sortedBy { it }
        }
            .foldIndexed(0) { index, acc, hand ->
                println("$hand gets ${(index + 1) * hand.bid}")
                acc + ((index + 1) * hand.bid)
            }
    }
}