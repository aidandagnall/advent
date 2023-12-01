package days

class Day09 : Day(9) {
    private val players: Int
    private val marbles: Int
    private val regex = Regex("""(\d+) players; last marble is worth (\d+) points""")
    init {
        regex.matchEntire(inputList.first())!!.destructured.let {  (a, b) ->
            players = a.toInt()
            marbles = b.toInt()
        }
    }

    override fun part1() : Any = run(players, marbles)

    override fun part2() : Any = run(players, marbles * 100)

    fun run(players: Int, marbles: Int): Long {
        val scores =  MutableList<Long>(players + 1) { 0 }
        val circle = ArrayDeque<Long>(marbles)
        circle.add(0L)

        var player = 0

        (1L..marbles.toLong()).forEach {
            if (it % 23L == 0L) {
                repeat(7) {
                    circle.addFirst(circle.removeLast())
                }
                scores[player] += it + circle.removeFirst()
            } else {
                circle.addLast(circle.removeFirst())
                circle.addLast(circle.removeFirst())
                circle.addFirst(it)
            }
            player = (player + 1) % players
        }
        return scores.max();
    }
}