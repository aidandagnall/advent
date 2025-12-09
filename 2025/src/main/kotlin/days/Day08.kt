package days

import util.Vector3
import util.getAllPairs
import util.product
import util.toVector3
import kotlin.collections.find
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Day08 : Day(8) {
    val boxes = inputList.map { it.toVector3() }.toSet()
    val sortedDistances = boxes.getAllPairs().let { pairs ->
        val values = mutableListOf<Triple<Vector3, Vector3, Double>>()
        pairs.forEach { (a, b) -> if (b.x > a.x) values.add(Triple(a, b, a.distance(b))) }
        values.sortedBy { (_, _, dist) -> dist}.map { (a, b, _) -> a to b }
    }

    override fun part1() : Any {
        val (network, _) = run(sortedDistances.take(1000))
        return network.sortedBy { it.size }.reversed().take(3).map { it.size }.product()
    }

    override fun part2() : Any {
        val (a, b) = run (sortedDistances).second.first()
        return a.x.toLong() * b.x.toLong()
    }

    fun run(connections: List<Pair<Vector3,Vector3>>, ): Pair<List<Set<Vector3>>, List<Pair<Vector3, Vector3>>> =
        connections.fold(boxes.map { setOf(it) }) { acc, (a, b) ->
            val aOwner = acc.find { a in it }!!
            val bOwner = acc.find { b in it }!!
            if (acc.size == 2 && aOwner != bOwner) {
                return acc to listOf(a to b)
            }
            acc.filter { a !in it }.filter { b !in it } + listOf(aOwner.union(bOwner))
        } to emptyList()

    fun Vector3.distance(other: Vector3): Double = sqrt(
        abs(this.x - other.x).toDouble().pow(2) +
                abs(this.y - other.y).toDouble().pow(2) +
                abs(this.z - other.z).toDouble().pow(2)
    )
}