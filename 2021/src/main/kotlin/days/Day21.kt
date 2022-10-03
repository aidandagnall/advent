package days

import kotlin.math.min

class Day21 : Day(21) {
    private val starts = inputList.map { it.last().digitToInt() }

    override fun part1() : Any {
        var dice = 1
        var (p1pos, p2pos) = starts
        var p1score = 0
        var p2score = 0
        while(p1score < 1000 && p2score < 1000) {
            p1pos += dice++ + dice++ + dice++
            p1pos %= 10
            if (p1pos == 0) p1pos = 10
            p1score += p1pos
            if (p1score >= 1000) break
            p2pos += dice++ + dice++ + dice++
            p2pos %= 10
            if (p2pos == 0) p2pos = 10
            p2score += p2pos
        }
        println("P1: $p1score, P2: $p2score, D: $dice")
        return min(p1score, p2score) * (dice - 1)
    }

    override fun part2() : Any {
        return findQuantumWins((starts[0] to 0), starts[1] to 0, mutableMapOf())
            .toList()
            .maxOf { it }
    }

    private fun findQuantumWins(p1: Pair<Int,Int>, p2: Pair<Int,Int>, explored: MutableMap<Pair<Pair<Int,Int>,Pair<Int,Int>>, Pair<Long,Long>>): Pair<Long,Long> {
        if (p2.second >= 21) return (0L to 1L)
        if ((p1 to p2) in explored) return explored[p1 to p2]!!
        var p1wins = 0L
        var p2wins = 0L
        (1..3).forEach { r1 ->
            (1..3).forEach { r2 ->
                (1..3).forEach { r3 ->
                    var (p1pos, p1score) = p1
                    p1pos = (p1pos + r1 + r2 + r3) % 10
                    if (p1pos == 0) p1pos = 10
                    p1score += p1pos
                    val expansions = findQuantumWins(p2, (p1pos to p1score), explored)
                    p1wins += expansions.second
                    p2wins += expansions.first
                }
            }
        }
        explored[p1 to p2] = p1wins to p2wins
        return p1wins to p2wins
    }

}