package days

import util.powerset

class Day16 : Day(16) {

    private val regex = """Valve (\S+) has flow rate=(\d+); tunnels? leads? to valves? (\S+(?:[, ]\S+)*)""".toRegex()
    val input = inputList.associate {
        regex.matchEntire(it)!!.groupValues.let { (_, name, rate, connections) ->
            name to (rate.toInt() to connections.split(", "))
        }
    }

    private val distances = input.keys.flatMap { a -> input.keys.map { b -> a to b } }.associate { (a, b) ->
        (a to b) to dfs(a, b)
    }

    override fun part1() : Any = search("AA", 30, 0, input.filter { (_, v) -> v.first != 0 }.keys)

    override fun part2() : Any {
        val closedVents = input.filter { it.value.first != 0 }.keys
        val meTasks = closedVents.powerset().filter { it.size <= closedVents.size / 2 }
        val elephantTasks = meTasks.map { closedVents.minus(it) }

        return meTasks.zip(elephantTasks).maxOf {  (me, elephant) ->
            search("AA", 26, 0, me) + search("AA", 26, 0, elephant)
        }
    }

    private fun dfs(start: String, dest: String): Int {
        if (start == dest) return 0
        val queue = mutableListOf(start)
        val distances = mutableMapOf<String, Int>()
        val visited = mutableSetOf<String>()
        distances[start] = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current !in visited) {
                visited.add(current)

                input[current]!!.second.forEach {
                    if (it == dest) return distances[current]!! + 1

                    if (it !in distances) {
                        distances[it] = distances[current]!! + 1
                        queue.add(it)
                    }
                }
            }
        }
        return Int.MAX_VALUE
    }

    private fun search(current: String, time: Int, released: Int, closed: Set<String>): Int {
        if (closed.isEmpty() || time < 0) return released

        return closed.maxOf {
            val dist = distances[current to it]!!
            if (time - dist < 0) return@maxOf released

            search(it, time - dist - 1, released + input[it]!!.first * (time - dist - 1), closed - it)
        }
    }

}