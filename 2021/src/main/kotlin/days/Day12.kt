package days

class Day12 : Day(12) {

    private val paths = mutableMapOf<String, MutableSet<String>>().apply {
        inputList.forEach {
            val (a, b) = it.split('-')
            getOrPut(a) { mutableSetOf() }.add(b)
            getOrPut(b) { mutableSetOf() }.add(a)
        }
    }

    private val routes = mutableSetOf<List<String>>()

    override fun part1(): Any {
        explore(List(1) { "start" }, false)
        return routes.size
    }

    override fun part2(): Any {
        explore(List(1) { "start" }, true)
        return routes.size
    }

    private fun explore(currentPath: List<String>, canRevisitNode: Boolean) {
        paths[currentPath.last()]!!.forEach { node ->
            var newCanRevisitNode = canRevisitNode
            if (node == "start") return@forEach
            if (node == "end") {
                routes.add(currentPath + node)
                return@forEach
            }
            if (node[0].isLowerCase() && node in currentPath) {
                if (!newCanRevisitNode) return@forEach
                newCanRevisitNode = false
            }
            explore(currentPath + node, newCanRevisitNode)
        }
    }

}