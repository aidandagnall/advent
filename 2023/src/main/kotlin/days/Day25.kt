package days

class Day25 : Day(25) {

    val graph = inputList.associate { line ->
        val (first, rest) = line.split(": ")
        first to rest.split(" ").toMutableList()
    }.toMutableMap()

    init {
        // ensure graph is bi-directional
        graph.keys.toSet().forEach { k ->
            graph[k]!!.forEach {
                if (it !in graph) {
                    graph[it] = mutableListOf(k)
                } else {
                    if (k !in graph[it]!!) {
                        graph[it]!!.add(k)
                    }
                }
            }
        }
    }

    override fun part1() : Any {
        // print output to visualise
//        println("source")
//        graph.keys.onEach { println(it) }
//        println("source,target")
//        println(graph.flatMap { (k, v) -> v.map { k to it } }.joinToString("\n"){ (a, b) -> "$a,$b" })
//        println()
//        println()
//        println()
//        println()
//        println()
//        println("source")
//        graph.keys.onEach { println(it) }

        //remove cuts manually
        val cuts = listOf(
            "jlt" to "sjr",
            "mzb" to "fjn",
            "zqg" to "mhb"
        )

        cuts.forEach { (a, b) ->
            graph[a]!!.remove(b)
            graph[b]!!.remove(a)
        }

        return cuts.first().toList().map {
            val seen = mutableSetOf(it)
            val queue = mutableListOf(it)

            while(queue.isNotEmpty()) {
                val next = queue.removeFirst()
                seen.add(next)
                queue.addAll(graph[next]!!.filter { it !in seen })
            }

            seen.count()
        }.reduce { acc, i -> acc * i}
    }

    override fun part2() : Any {
        return "Merry Christmas"
    }
}