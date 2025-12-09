package util

import kotlin.math.abs

data class Vector(val x: Int, val y: Int) {
    operator fun plus(other: Vector) = this.x + other.x vto this.y + other.y
    operator fun minus(other: Vector): Vector = this.x - other.x vto this.y - other.y
    operator fun times(scalar: Int) = x * scalar vto y * scalar

    fun manhattan(other: Vector): Int {
        return abs(this.x - other.x) + abs(this.y- other.y)
    }

    fun neighbours4(): List<Vector> = listOf(
        x + 1 vto y,
        x - 1 vto y,
        x vto y + 1,
        x vto y - 1,
    )

    fun neighbours8(): List<Vector> =
        (-1..1).flatMap { x1 ->
            (-1..1).map { y1 ->
                x + x1 vto y+ y1
            }
        }.filterNot { it == this }

    fun toDirection(): Direction = Direction.entries.first { it.toVector() == this }

    fun slopeTo(other: Vector): Float? =
        if(x == other.x) null
        else (y.toFloat() - other.y.toFloat()) / (x.toFloat() - other.x.toFloat())
}

data class Vector3(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3) = Vector3(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun minus(other: Vector3) = Vector3(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun times(scalar: Int) = x * scalar vto y * scalar

    fun manhattan(other: Vector3): Int {
        return abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)
    }
}

infix fun Int.vto(other: Int) = Vector(this, other)
infix fun Vector.vto(other: Int) = Vector3(x, y, other)
fun String.toVector() = split(",").map { it.toInt() }.let { (x, y) -> Vector(x,y) }
fun String.toVector3() = split(",").map { it.toInt() }.let { (x, y, z) -> Vector3(x,y,z) }
