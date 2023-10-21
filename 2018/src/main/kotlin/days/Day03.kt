package days

class Day03 : Day(3) {

    val cuts = mutableMapOf<Pair<Int,Int>, Int>()
    val regex = Regex("""#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""")

    override fun part1() : Any {
        inputList.forEach {
            regex.find(it)?.destructured?.let { (_, x, y, w, h) ->

                (x.toInt() until  (x.toInt() + w.toInt())).forEach { a ->
                    (y.toInt() until (y.toInt() + h.toInt())).forEach { b ->

                        if ((a to b) in cuts) {
                            cuts[a to b] = cuts[a to b]!! + 1
                        } else {
                            cuts[a to b] = 1
                        }
                    }
                }
            }
        }

        return cuts.count { (_, v) -> v >= 2 }
    }

    override fun part2() : Any {
        inputList.forEach {
            regex.find(it)?.destructured?.let { (id, x, y, w, h) ->

                if ((x.toInt() until  (x.toInt() + w.toInt())).all { a ->
                    (y.toInt() until (y.toInt() + h.toInt())).all { b ->
                        cuts[a to b] == 1
                    }
                }) {
                    return id
                }

            }
        }
        return ""
    }
}
