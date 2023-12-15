package days

import util.filterNotEmpty
import util.replace
import util.replaceAt

class Day15 : Day(15) {
    sealed class Step(input: String) {
        val label = input.takeWhile { it != '-' && it != '=' }
        val inputHash = hash(input)
        val labelHash = hash(label)

        private fun hash(input: String): Int = input.fold(0) { acc, it ->
            ((acc + it.code) * 17) % 256
        }
    }
    class Minus(input: String): Step(input)
    class Equals(input: String): Step(input) {
        val focalLength = input.last().digitToInt()
    }

    private val initialisation = inputList.first().split(",").filterNotEmpty().map {
        when {
            '-' in it -> Minus(it)
            else -> Equals(it)
        }
    }

    override fun part1() : Any = initialisation.sumOf(Step::inputHash)

    override fun part2(): Any = initialisation
        .fold(List<List<Pair<String,Int>>>(256) { listOf() }) { boxes, ins->
            when(ins) {
                is Minus -> boxes.replaceAt(ins.labelHash, boxes[ins.labelHash].filter { it.first != ins.label })
                is Equals -> boxes.replaceAt(
                    ins.labelHash,
                    boxes[ins.labelHash].find { it.first == ins.label }?.let {
                        boxes[ins.labelHash].replace(it, ins.label to ins.focalLength)
                    } ?: (boxes[ins.labelHash] + (ins.label to ins.focalLength))
                )
            }
        }.mapIndexed { boxIndex, box ->
            box.mapIndexed { lensIndex, (_, lens) ->
                (boxIndex + 1) * (lensIndex + 1) * lens
            }.sum()
        }.sum()

    private fun hash(input: String): Int = input.fold(0) { acc, it ->
        ((acc + it.code) * 17) % 256
    }
}