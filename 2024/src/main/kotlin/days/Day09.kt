package days

class Day09 : Day(9) {
    private val input = inputList.first().toCharArray().toList()
        .mapIndexed { index, s ->
            when (index % 2) {
                1 -> Space(s.digitToInt())
                else -> File(s.digitToInt(), index.toLong() / 2L)
            }
        }

    sealed class Storage(open val count: Int)
    data class File(override val count: Int, val id: Long, val moved: Boolean = false): Storage(count)
    data class Space(override val count: Int): Storage(count)

    private fun List<Storage>.fullMove(space: Storage, file: File, src: Int, dest: Int) = toMutableList().apply {
        this[dest] = file.copy(moved = true)
        this.add(dest + 1, Space(space.count - file.count))
        this[src + 1] = Space(file.count)
        this.removeLast()
        this.removeAll { it.count <= 0}
    }

    private fun List<Storage>.partMove(space: Storage, file: File, dest: Int) = toMutableList().apply {
        this[dest] = file.copy(count = space.count, moved = true)
        this[this.size - 1] = file.copy(count = file.count - space.count)
    }

    override fun part1() : Any =
        generateSequence(input) { acc ->
            val (spaceIndex, space) = acc.withIndex().find { it.value is Space } ?: return@generateSequence null

            val file = acc.last() as File

            when {
                space.count >= file.count -> acc.fullMove(space, file, acc.size - 1, spaceIndex)
                else -> acc.partMove(space, file, spaceIndex)
            }.dropLastWhile { it is Space }
        }.last().checksum()

    override fun part2() : Any =
        generateSequence(input) { acc ->
            val (j , l) = acc.withIndex().lastOrNull { (_, value) -> value is File && !value.moved } ?: return@generateSequence null
            require(l is File)

            for ((i, space) in acc.withIndex()) {
                if (j < i) break
                if (space is File) continue

                if (acc[i].count >= l.count) {
                    return@generateSequence acc.fullMove(space, l, j, i)
                }
            }

            acc.toMutableList().apply {
                this[j] = l.copy(moved = true)
            }
        }.last().checksum()

    private fun List<Storage>.checksum() = fold(0 to 0L) { (pos, sum), file ->
        when (file) {
            is Space -> (pos + file.count) to sum
            is File -> (1..file.count).fold(pos to sum) { (pos, sum), _ ->
                pos + 1 to sum + (file.id * pos)
            }
        }
    }.second
}