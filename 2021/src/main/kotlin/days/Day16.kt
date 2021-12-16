package days

class Day16 : Day(16) {

    override fun part1() = Packet(inputList.first()).sumVersion

    override fun part2() = Packet(inputList.first()).result

    class Packet(input: MutableList<Char>) {
        constructor(str: String) : this(convertInput(str))

        private val version: Int = input.takeRemove(3).joinToString("").toInt(2)
        private val type = input.takeRemove(3).joinToString("").toInt(2)

        private val payload: Long = if (type == 4) getPayload(input) else 0

        private fun getPayload(input: MutableList<Char>) =
            input.takeRemove((input.chunked(5).indexOfFirst { it[0] == '0' } + 1) * 5)
                .chunked(5).joinToString("") { it.drop(1).joinToString("") }.toLong(2)

        private val subPackets = mutableListOf<Packet>().apply {
            if (type == 4) return@apply
            if ('0' in input.takeRemove(1)) {
                val length = input.takeRemove(15).joinToString("").toInt(2)
                val originalLength = input.size
                while (input.size > originalLength - length) add(Packet(input))
            } else {
                val num = input.takeRemove(11).joinToString("").toInt(2)
                (0 until num).forEach { _ -> add(Packet(input)) }
            }
        }

        val sumVersion: Int = version + subPackets.sumOf { it.sumVersion }
        var result: Long = when (type) {
            0 -> subPackets.sumOf { it.result }
            1 -> subPackets.fold(1) { acc, next -> acc * next.result }
            2 -> subPackets.minOf { it.result }
            3 -> subPackets.maxOf { it.result }
            4 -> payload
            5 -> subPackets.run { if (get(0).result > get(1).result) 1L else 0L }
            6 -> subPackets.run { if (get(0).result < get(1).result) 1L else 0L }
            7 -> subPackets.run { if (get(0).result == get(1).result) 1L else 0L }
            else -> {
                println("-- I FOUND AN ERROR --"); 99999999L
            }
        }

        private fun <T> MutableList<T>.takeRemove(n: Int): List<T> {
            val removed: List<T> = this.take(n)
            (0 until n).forEach { _ -> this.removeFirst() }
            return removed
        }

        companion object {
            @JvmStatic
            fun convertInput(str: String): MutableList<Char> {
                return str.flatMap { hexDigit ->
                    Integer.parseInt(hexDigit.toString(), 16)
                        .toString(2)
                        .padStart(4, '0')
                        .toList()
                }.toMutableList()
            }
        }
    }
}