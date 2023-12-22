package days

class Day22 : Day(22) {

    data class Brick(val points: List<Triple<Int, Int, Int>>) {
        val above = mutableSetOf<Brick>()
        val below = mutableSetOf<Brick>()
        fun drop(): Brick = this.copy(points = points.map { (x, y, z) -> Triple(x, y, z - 1) })
    }

    private val points = mutableMapOf<Triple<Int, Int, Int>, Brick>()

    private val bricks = inputList.mapIndexed { i, line ->
        val (first, last) = line.split("~")
        val (fx, fy, fz) = first.split(",").map { it.toInt() }
        val (lx, ly, lz) = last.split(",").map { it.toInt() }

        (fx..lx).flatMap { x ->
            (fy..ly).flatMap { y ->
                (fz..lz).map { z ->
                    Triple(x, y, z)
                }
            }
        }.let { Brick(it) }
    }.sortedBy { it.points.first().third }

    init {
        val compacted = bricks.fold(bricks) { acc, b ->
            val dropped = generateSequence(b, Brick::drop)
                .drop(1)
                .takeWhile { it.points.all { it !in points && it.third > 0 } }
                .lastOrNull() ?: b
            dropped.points.forEach { points[it] = dropped }
            acc - b + dropped
        }

        compacted.forEach { b ->
            val below = b.points.map { (x, y, z) -> Triple(x, y, z - 1) }
            val above = b.points.map { (x, y, z) -> Triple(x, y, z + 1) }

            b.above.addAll(above.mapNotNull { points[it] }.filter { it != b }.distinct())
            b.below.addAll(below.mapNotNull { points[it] }.filter { it != b }.distinct())
        }
    }

    override fun part1(): Any = points.values.toSet().count { disintegrated ->
        disintegrated.above.all { it.below.size > 1 }
    }

    override fun part2(): Any = points.values.toSet().sumOf { disintegrated ->
        val queue = mutableListOf(disintegrated)
        val falling = mutableSetOf(disintegrated)

        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()

            if (next.below.all { it in falling } || next in falling) {
                falling.add(next)
                queue.addAll(next.above)
            }
        }

        falling.count() - 1
    }
}