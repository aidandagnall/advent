package days

class Day08 : Day(8) {

    data class Node(val children: List<Node>, val metadata: List<Int>) {
        fun nestedMetadata(): List<Int> = metadata + children.flatMap { it.nestedMetadata() }
        fun value(): Int = if (children.isEmpty()) metadata.sum() else metadata.sumOf { children.getOrNull(it - 1)?.value() ?: 0 }
    }

    private fun parseNode(input: List<Int>): Pair<Node, List<Int>> {

        val (childCount, metaCount) = input.take(2)
        val (children, remaining) = (0 until childCount).fold(listOf<Node>() to input.drop(2)) { (nodes, input), _ ->
            val (a, b) = parseNode(input)
            (nodes + a) to b
        }

        return Node(children, remaining.take(metaCount)) to remaining.drop(metaCount)
    }

    private val root = parseNode(inputList.first().split(" ").map { it.toInt() }).first

    override fun part1() : Any = root.nestedMetadata().sum()

    override fun part2() : Any = root.value()
}