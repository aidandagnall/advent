package days


class Day23 : Day(23) {

    private val connections = buildMap<String, Set<String>> {
        inputList.forEach {
            val (a, b) = it.split("-")
            this[a] = this.getOrDefault(a, emptySet()) + b
            this[b] = this.getOrDefault(b, emptySet()) + a
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

    override fun part2() : Any =
        bronKerbosch(connections.keys,emptySet(), emptySet())
            .maxBy { it.size }
            .sorted()
            .joinToString(",")

    // I had to google what this is
    private fun bronKerbosch(nodes: Set<String>, clique: Set<String>, alreadyRemoved: Set<String>): Set<Set<String>> {
        val cliques: MutableSet<Set<String>> = HashSet()
        val candidates = nodes.toMutableList()
        val removed = alreadyRemoved.toMutableList()

        if (candidates.isEmpty() && removed.isEmpty()) cliques.add(HashSet(clique))

        while (candidates.isNotEmpty()) {
            val current = candidates.first()
            cliques.addAll(
                bronKerbosch(
                    candidates.filter { it in connections[current]!! }.toSet(),
                    clique + current,
                    removed.filter { it in connections[current]!! }.toSet()
                )
            )
            candidates.remove(current)
            removed.add(current)
        }
        return cliques
    }
}