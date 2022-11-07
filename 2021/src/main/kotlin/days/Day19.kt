package days

import kotlin.math.abs

// Long awaited 

// Solved mostly with the help of https://todd.ginsberg.com/post/advent-of-code/2021/day19/
// to fix my terrible solution

class Day19 : Day(19) {
    private val solution: Scanner
    private val scanners: Set<Triple<Int,Int,Int>>
    init {
        val l = mutableListOf<Scanner>()
        var b : MutableList<Triple<Int,Int,Int>> = mutableListOf()
        var id : Int = 0
        inputList.forEach {
            when {
                "scanner" in it -> b = mutableListOf()
                it.isEmpty() -> l.add(Scanner(b.toMutableSet()))
                else -> {
                    val (x, y, z) = it.split(",").map { coord -> coord.toInt() }
                    b.add(Triple(x, y, z))
                }
            }
        }
        l.add(Scanner(b.toMutableSet()))

        val (base, s) = solveMap(l.toList())
        solution = base
        scanners = s
    }

    private fun solveMap(scanners: List<Scanner>): Pair<Scanner, Set<Triple<Int,Int,Int>>>  {
        val base = scanners.first()
        val found = mutableSetOf(Triple(0, 0, 0))
        val notFound = scanners.drop(1).toMutableList()

        while (notFound.isNotEmpty()) {
            val s = notFound.first()

            val intersection = base.findIntersection(s)
            if (intersection == null) {
                notFound.add(notFound.removeFirst())
                continue
            }

            base.beacons.addAll(intersection.second.beacons)
            found.add(intersection.first)
            notFound.remove(s)
        }
        return base to found
    }

    override fun part1() : Any {
        return solution.beacons.size
    }

    override fun part2() : Any {
        return scanners.flatMap{ a ->
            scanners.map { b ->
                a to b
            }
        }.maxOf { (a, b) -> a.distanceTo(b) }
    }

    class Scanner(val beacons: MutableSet<Triple<Int,Int,Int>>,) {
        override fun toString(): String {
            return "Scanner $beacons"
        }

        fun findIntersection(other: Scanner): Pair<Triple<Int,Int,Int>, Scanner>? {

            return (0..5).firstNotNullOfOrNull { direction ->
                (0..3).firstNotNullOfOrNull { rotation ->

                    val otherBeaconsRotated = other.beacons.map { it.direction(direction).rotations(rotation) }

                    beacons.firstNotNullOfOrNull { a ->
                        otherBeaconsRotated.firstNotNullOfOrNull { b ->
                            val diff = a - b
                            val beaconsAdjusted = otherBeaconsRotated.map { it + diff }.toMutableSet()
                            if (beaconsAdjusted.intersect(beacons).size >= 12) {
                                diff to Scanner(beaconsAdjusted)
                            } else null
                        }
                    }

                }

            }
        }
    }

    companion object {
        operator fun Triple<Int,Int,Int>.plus(a: Triple<Int,Int,Int>): Triple<Int,Int,Int> {
            return Triple(this.first + a.first, this.second + a.second, this.third + a.third)
        }

        operator fun Triple<Int,Int,Int>.minus(a: Triple<Int,Int,Int>): Triple<Int,Int,Int> {
            return Triple(this.first - a.first, this.second - a.second, this.third - a.third)
        }

        fun Triple<Int,Int,Int>.distanceTo(a: Triple<Int,Int,Int>): Int {
            return abs(this.first - a.first) +
                    abs(this.second - a.second) +
                    abs(this.third- a.third)
        }

        private fun Triple<Int,Int,Int>.rotations(direction: Int): Triple<Int,Int,Int> {
            let { (x, y, z) ->
                return when (direction) {
                    0 -> Triple(x, y, z)
                    1 -> Triple(-y, x, z)
                    2 -> Triple(-x, -y, z)
                    3 -> Triple(y, -x, z)
                    else -> Triple(0, 0, 0)
                }}
        }

        private fun Triple<Int,Int,Int>.direction(direction: Int): Triple<Int,Int,Int> {
            let { (x, y, z) ->
                return when (direction) {
                    0 -> Triple(x, y, z)
                    1 -> Triple(x, -y, -z)
                    2 -> Triple(x, -z, y)
                    3 -> Triple(-y, -z, x)
                    4 -> Triple(y, -z, -x)
                    5 -> Triple(-x, -z, -y)
                    else -> Triple(0, 0, 0)
                }
            }

        }
    }

}

