package days

class Day12 : Day(12) {

    private val paths = mutableMapOf<String, MutableSet<String>>().apply {
        inputList.forEach {
            val (a, b) = it.split('-')
            getOrPut(a) { mutableSetOf() }.add(b)
            getOrPut(b) { mutableSetOf() }.add(a)
        }
    }

    override fun part1(): Any {
        return explore(listOf("start"), false).size
    }

    override fun part2(): Any {
        return explore(listOf("start"), true).size
    }

    private fun explore(
        path: List<String>,
        canRevisit: Boolean,
        routes: MutableSet<List<String>> = mutableSetOf(),
    ): MutableSet<List<String>> {
        paths[path.last()]!!.forEach { node ->
            if (node == "end") routes.add(path + node)
            if (node == "start" || node == "end") return@forEach
            if (node[0].isLowerCase() && node in path && !canRevisit) return@forEach
            explore(path + node, canRevisit && !(node[0].isLowerCase() && node in path), routes)
        }
        return routes
    }

}