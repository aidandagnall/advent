package days

class Day24 : Day(24) {

    data class Vector(val x: Double, val y: Double, val z: Double)
    data class Hailstone(val position: Vector, val velocity: Vector)

    val hailstones = inputList.map { line ->
        val (pos, vel) = line.split(" @ ")
        val position = pos.split(", ").map { it.toDouble() }.let { (x, y, z) ->Vector(x, y, z) }
        val velocity= vel.split(", ").map { it.toDouble() }.let { (x, y, z) ->Vector(x, y, z) }
        Hailstone(position, velocity)
    }

    fun lineIntersect(h1: Hailstone, h2: Hailstone): Vector? {
        val (x1, y1, _) = h1.position
        val (x2v, y2v, _) = h1.velocity
        val (x3, y3, _) = h2.position
        val (x4v, y4v, _) = h2.velocity

        val x2 = x2v + x1
        val y2 = y2v + y1
        val x4 = x4v + x3
        val y4 = y4v + y3

        val denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1)

        if (denom == 0.0) {
            return null
        }

        val ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom
        val ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom

        return Vector(x1 + ua * (x2 - x1), y = y1 + ua * (y2 - y1), 0.0)
    }

    val testRange = 200000000000000..400000000000000
//    val testRange = 7..27

    override fun part1() : Any {
        val collisions = mutableSetOf<Pair<Hailstone,Hailstone>>()
        hailstones.forEach {  h ->
            hailstones.forEach inner@ {  i ->
                if (h == i) return@inner
                val (min, max) = listOf(h, i).sortedWith( compareBy<Hailstone> { it.position.x }.thenBy { it.position.y }.thenBy { it.position.z })
                if (min to max in collisions) return@inner

                val intersection = lineIntersect(h, i) ?: return@inner

                if (h.velocity.x > 0)  {
                    if (intersection.x < h.position.x) return@inner
                } else if (intersection.x > h.position.x) return@inner

                if (i.velocity.x > 0) {
                    if (intersection.x < i.position.x) return@inner
                } else if (intersection.x > i.position.x) return@inner

                if (intersection.x.toLong() in testRange && intersection.y.toLong() in testRange) {
                    collisions.add(min to max)
                }
            }
        }

        return collisions.count()
    }

    override fun part2() : Any {
        return hailstones.take(3).flatMapIndexed { i, it ->
            listOf(
                "t$i * rvx + rpx = t$i * ${it.velocity.x} + ${it.position.x}",
                "t$i * rvy + rpy = t$i * ${it.velocity.y} + ${it.position.y}",
                "t$i * rvz + rpz = t$i * ${it.velocity.z} + ${it.position.z}",
            )
        }.joinToString("\n")
    }
}