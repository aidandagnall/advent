package util

fun Int.positiveMod(other: Int) = if (this % other < 0) other + (this % other) else this % other
fun Int.lcm(b: Int): Int = this * (b / this.gcd(b))
fun Long.lcm(b: Long): Long = this * (b / this.gcd(b))
fun Int.gcd(b: Int): Int {
    var (left, right) = this to b
    while (right > 0) {
        val temp = right
        right = left % right
        left = temp
    }
    return left
}
fun Long.gcd(b:Long): Long{
    var (left, right) = this to b
    while (right > 0) {
        val temp = right
        right = left % right
        left = temp
    }
    return left
}

fun Int.safeRangeTo(other: Int): IntRange =
    if (this <= other) this..other
    else other..this
