package days

import days.Day16.Packet.Companion.packets

class Day16 : Day(16) {

    class Packet(var input: MutableList<Char>) {
        companion object {
            @JvmStatic val packets = mutableListOf<Packet>()
        }
        val version = input.take(3).joinToString("").toInt(2)
        private val type = input.drop(3).take(3).joinToString("").toInt(2)

        private val payload : Long = if (type == 4) {
            val inputs: List<List<Char>> = input.drop(6).chunked(5)
            val values: MutableList<List<Char>> = inputs.takeWhile { it[0] == '1' }.toMutableList()
            values += inputs.first { it[0] == '0' }
            input = input.drop(6).drop(5 * values.size).toMutableList()
            values.joinToString("") { it.drop(1).joinToString("") }.toLong(2)
        } else { 0 }

        private val subPackets = mutableListOf<Packet>().apply {
            if (type == 4) return@apply
            if (input.drop(6)[0] == '0') {
                val length = input.drop(7).take(15).joinToString("").toInt(2)
                val shortInput = input.drop(22).take(length).toMutableList()
                input = input.drop(22 + length).toMutableList()
                add(Packet(shortInput))
                while (last().input.isNotEmpty()) add(Packet(last().input))
            } else {
                val num = input.drop(7).take(11).joinToString("").toInt(2)
                input = input.drop(18).toMutableList()
                add(Packet(input))
                (1 until num).forEach { _ -> add(Packet(last().input)) }
                input = last().input
            }
        }

        var result : Long = when (type) {
            0 -> subPackets.sumOf { it.result }
            1 -> subPackets.fold(1) { acc, next -> acc * next.result }
            2 -> subPackets.minOf { it.result }
            3 -> subPackets.maxOf { it.result }
            4 -> payload
            5 -> subPackets.run { if (get(0).result > get(1).result) 1L else 0L }
            6 -> subPackets.run { if (get(0).result < get(1).result) 1L else 0L }
            7 -> subPackets.run { if (get(0).result == get(1).result) 1L else 0L }
            else -> { println("-- I FOUND AN ERROR --"); 99999999L }
        }
        private fun <T> MutableList<T>.takeRemove(n: Int) : List<T> {
            val removed : List<T> = this.take(n)
            this.removeAll(this.take(n))
            return removed
        }
        private fun <T> MutableList<T>.dropRemove(n: Int) : List<T> {
            this.removeAll(this.take(n))
            return this.toList()
        }
        init { packets.add(this) }
    }

    val input: MutableList<Char> = inputList.first()
        .flatMap {
            Integer.parseInt(it.toString(), 16).toString(2).padStart(4, '0')
                .split("")
                .filter { it != "" }
                .map { it[0] }
        }.toMutableList()

    init {
        Packet(input)
        while(packets.last().input.all { it != '0' } && packets.last().input.isNotEmpty()) {
            Packet(packets.last().input)
        }
    }

    override fun part1() = packets.sumOf { it.version }

    override fun part2() = packets.last().result
}