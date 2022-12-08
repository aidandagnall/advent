package days

class Day08 : Day(8) {
    private val input = inputList.map { it.toList().map(Char::code) }.let { trees ->
        (trees.indices).map { x ->
            (trees[0].indices).map { y ->
                trees[x][y] to listOf(                         // height to trees in each direction (nearest first)
                    trees[x].take(y).reversed(),               // trees to the left
                    trees[x].drop(y + 1),                   // trees to the right
                    trees.map { it[y] }.take(x).reversed(),    // trees above
                    trees.map { it[y] }.drop(x + 1),        // trees below
                )
            }
        }.flatten()
    }

    override fun part1() : Any {
        return input.count { (height, directions) ->
            directions.any { d -> d.all { it < height } }
        }
    }

    override fun part2() : Any {
        return input.maxOf { (height, directions) ->
            directions.map { d ->
                d.takeWhile { it < height }.count() + if (d.any { it >= height}) 1 else 0
            }.fold(1) { acc, i -> acc * i}
        }
    }
}
