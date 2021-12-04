package days

class Day04 : Day(4) {
    override fun part1() : Any {
        return solveGames().sortedBy { it.second }
            .map { it.first * it.third}
            .first()
    }

    override fun part2() : Any {
        return solveGames().sortedBy { it.second }
            .map { it.first * it.third}
            .last()
    }

    // Return list of solved games in the form
    //      < Number called to win game, number of calls to win, sum of remaining card >
    private fun solveGames() : List<Triple<Int, Int, Int>> {
        val order = inputList.first().split(",").map { it.toInt() }
        val cards = inputList.filterIndexed { i, it -> it != "" && i > 0 }
            .map { it.split(" ").filter { i -> i != "" }.map { i -> i.toInt() } }
            .chunked(5)
            .map { it.flatten().toMutableList() }
        return cards.map { card ->
            order.forEachIndexed { i, called ->
                card.replaceAll { if (it == called) -1 else it }
                if (hasWon(card.toList())) return@map Triple(called, i, card.filter { it != -1 }.sum())
            }
            Triple(-1, -1, -1)
        }
    }

    private fun hasWon(card : List<Int>) : Boolean {
        val gridCard = card.chunked(5)
        return gridCard.any { row -> row.all { it == -1 } } or
                (0..4).any { gridCard.all { row -> row[it] == -1 } }
    }
}