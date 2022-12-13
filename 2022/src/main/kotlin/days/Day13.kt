package days
import kotlin.Exception

class Day13 : Day(13) {

    private val pairs = inputList.filter { it != "" }.map { Packet(it) }

    override fun part1() : Any {
        return pairs.chunked(2).mapIndexed { i, (a, b) -> if (a < b) i + 1 else 0 }.sum()
    }

    override fun part2() : Any {
        return (pairs + Packet("[[2]]") + Packet("[[6]]"))
            .sorted()
            .mapIndexed { i, it -> (i + 1) to it }
            .filter { it.second in listOf(Packet("[[2]]"), Packet("[[6]]")) }
            .take(2)
            .fold(1) { acc, it -> acc * it.first }
    }
    data class Packet(val intValue: Int?, val listValue: List<Packet>?): Comparable<Packet> {
        override fun compareTo(other: Packet): Int {
            val (aInt, aList) = this
            val (bInt, bList) = other

            return when {
                aInt is Int && bInt is Int -> intValue!!.compareTo(other.intValue!!)
                aList is List<Packet> && bList is List<Packet> -> {
                    aList.forEachIndexed { i, it ->
                        if (i >= bList.size) return 1
                        return it.compareTo(bList[i]).let { if (it == 0) null else it} ?: return@forEachIndexed
                    }
                    if (aList.size == bList.size) return 0
                    return -1
                }
                aInt == null -> this.compareTo(Packet(null, listOf(other)))
                aList == null -> Packet(null, listOf(this)).compareTo(other)
                else -> throw Exception("Failed to match pattern. $this vs $other")
            }
        }

       companion object {
           operator fun invoke(input: String) : Packet {
                val str = input.removePrefix("[").removeSuffix("]")

                if (str.isEmpty()) return Packet(null, listOf())
                if ("[],".all { it !in str }) return Packet(str.toInt(), null)

                var depth = 0
                var substringIndex = 0
                val packets = mutableListOf<Packet>()
                str.forEachIndexed { i, c ->
                    when {
                        c == '[' -> depth++
                        c == ']' -> depth--
                        c == ',' && depth == 0 -> {
                            packets.add(Packet(str.substring(substringIndex, i)))
                            substringIndex = i + 1
                        }
                    }
                }
                return Packet(null, packets + Packet(str.substring(substringIndex)))
            }
        }
    }
}