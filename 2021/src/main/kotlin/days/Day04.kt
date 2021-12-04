package days

class Day04 : Day(4) {
    override fun part1() : Any {
        val order = inputList.first().split(",").map { it.toInt() }
        val cards = inputList.filterIndexed { i, it -> it != "" && i > 0 }
            .map { it.split(" ")
                .filter {i -> i != ""}
                .map{ i -> i.toInt()}.toMutableList()
            }
            .chunked(5)

        order.forEach { called ->
            cards.onEach { card ->
                card.map { row ->
                    val i = row.indexOf(called)
                    if ( i != -1 ) {
                        row[i] = -1
                    }
                }

                if (card.any { row -> row.all { it == -1} } or (0..4).any { i -> card.all { row -> row[i] == -1 } }) {
                    return called * card.sumOf { r -> r.filter{ it != -1}.sumOf{it} }
                }
            }
        }
        return 0
    }

    override fun part2() : Any {
        val order = inputList.first().split(",").map { it.toInt() }
        val cards = inputList.filterIndexed { i, it -> it != "" && i > 0 }
            .map { it.split(" ")
                .filter {i -> i != ""}
                .map{ i -> i.toInt()}.toMutableList()
            }
            .chunked(5)
        var cardNumbers = (cards.indices).toList()

        order.forEachIndexed { index, called ->
            cards.onEach { card ->
                card.forEach { row ->
//                    row.map { if (it == called) -1 else it }
                    val i = row.indexOf(called)
                    if ( i != -1 ) {
                        row[i] = -1
                    }
                }
                println(card)
            }
            cards.forEachIndexed { i, card ->
                if (card.any { row -> row.all { it == -1} } or (0..4).any { i -> card.all { row -> row[i] == -1 } }) {
                    if (cardNumbers.size == 1 && i in cardNumbers) {
                        return called * card.sumOf { r -> r.filter{ it != -1}.sumOf{it}}
                    }
                    cardNumbers = cardNumbers.filter {it != i }
                }
            }

        }
        return 0
    }
}