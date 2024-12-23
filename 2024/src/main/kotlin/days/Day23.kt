package days

class Day23 : Day(23) {

    private val connections = buildMap<String, List<String>> {
        inputList.forEach {
            val (a, b) = it.split("-")
            this[a] = this.getOrDefault(a, emptyList()) + b
            this[b] = this.getOrDefault(b, emptyList()) + a
        }
    }

    override fun part1() : Any =
        connections.flatMap { (c, conn) ->
            conn.flatMap { a ->
                conn.mapNotNull { b ->
                    if (a == b) null
                    else if (b in connections[a]!!) listOf(c, a, b).sorted() else null
                }
            }
        }.toSet().count { it.any { it.first() == 't' } }

    val cache = mutableMapOf<Pair<String, Set<String>>, List<String>>()

    private fun traverseGraph(start: String, set: Set<String>): List<String> {
        if (start to set in cache) return cache[start to set]!!
        return (connections[start]!!
            .filter { set.all { start in connections[it]!! } && it !in set }
            .map { traverseGraph(it, set + start) }
            .maxByOrNull { it.size }?.toList() ?: set.toList())
            .also { cache[start to set] = it }
    }

    override fun part2() : Any {
        return connections.map { (k, _) ->
            println(k)
            traverseGraph(k, emptySet()) }
            .maxBy { it.size }
            .sorted()
            .joinToString(",")
    }
}