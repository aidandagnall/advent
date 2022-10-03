package days

import java.lang.Integer.max
import java.lang.Integer.min

class Day22 : Day(22) {
    private data class Point(val x: Int, val y: Int, val z: Int)
    private class Cuboid(val state: STATE, val from: Point, val to: Point) {
        enum class STATE {On, Off}
        fun size(): Long {
            return ((to.x - from.x + 1).toLong() * (to.y - from.y + 1).toLong() * (to.z - from.z + 1).toLong() * if (state == STATE.On) 1 else -1).toLong()
        }
        fun oppState(): STATE = if (state == STATE.On) STATE.Off else STATE.On
        fun intersects(other: Cuboid): Boolean {
            return to.x >= other.from.x && from.x <= other.to.x &&
                    to.y >= other.from.y && from.y <= other.to.y &&
                    to.z >= other.from.z && from.z <= other.to.z
        }

        fun getIntersection(other: Cuboid): Cuboid {
            val xs = listOf(from.x, to.x, other.from.x, other.to.x).sorted()
            val ys = listOf(from.y, to.y, other.from.y, other.to.y).sorted()
            val zs = listOf(from.z, to.z, other.from.z, other.to.z).sorted()
            return Cuboid(other.state, Point(xs[1], ys[1], zs[1]), Point(xs[2], ys[2], zs[2]))
        }
    }

    private val input : List<Cuboid> = inputList.map { line ->
        val first = if (line.split(" ").first() == "on") Cuboid.STATE.On else Cuboid.STATE.Off
        val coords = line.split(" ").drop(1)
            .map { it.split(",")
                .map { it.drop(2) }
                .map { it.split("..").map { it.toInt() } }
            }.flatten()
        Cuboid(first, Point(coords[0][0],coords[1][0],coords[2][0]), Point(coords[0][1],coords[1][1],coords[2][1]))
    }
    override fun part1() : Any {
        val cubes = mutableListOf<Cuboid>()
        input.forEach { if (it.from.x in -50..50) runStep(it, cubes) }
        return cubes.sumOf { it.size() }
    }

    private fun runStep(cube: Cuboid, cubes: MutableList<Cuboid>) {
        val oldCubes = cubes.toList()
        oldCubes.forEach {
            if (it.intersects(cube)) {
                cubes.add(it.getIntersection(Cuboid(it.oppState(), cube.from, cube.to)))
            }
        }
        if (cube.state == Cuboid.STATE.On) cubes.add(cube)
    }

    override fun part2() : Any {
        val cubes = mutableListOf<Cuboid>()
        input.forEach { runStep(it, cubes) }
        return cubes.sumOf { it.size() }
    }

}