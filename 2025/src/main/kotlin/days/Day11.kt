package days

class Day11 : Day(11) {
    val nodes = inputList.map { it.split(": ") }.associate { (name, rest) -> name to rest.split(" ") }

    override fun part1() : Any = dfs("you")
    override fun part2() : Any = dfs("svr" , dac = false , fft =false)

    val cache = mutableMapOf<Triple<String, Boolean?, Boolean?>, Long>()
    fun dfs(node: String, dac: Boolean? = null, fft: Boolean? = null, seen: Set<String> = emptySet()): Long{
        if (Triple(node, dac, fft) in cache) {
            return cache[Triple(node, dac, fft)]!!
        }
        if (node == "out") {
            return if (dac != false && fft != false) 1L else 0L
        }
        val next = nodes[node]!!.filter { it !in seen }

        return next.sumOf { n ->
            dfs(n, dac?.let { it || node == "dac" }, fft?.let { it || node == "fft" }, seen + node)
        }.also { cache[Triple(node, dac, fft)] = it }
    }
}