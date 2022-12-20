package days

import util.product
import java.lang.Integer.min
import kotlin.math.max

class Day19 : Day(19) {
    private val regex = """Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.""".toRegex()
    private val blueprints = inputList.map { line ->
        regex.matchEntire(line)!!.groupValues.let {
            Blueprint(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt() to it[5].toInt(), it[6].toInt() to it[7].toInt())
        }
    }

    data class Blueprint(
         val id: Int,
         val oreRobotCost: Int,                 // cost in ore
         val clayRobotCost: Int,                // cost in ore
         val obsidianRobotCost: Pair<Int,Int>,  // cost in (ore, clay)
         val geodeRobotCost: Pair<Int,Int>,     // cost in (ore, obsidian)
    ) {
        val maxOreCost = listOf(clayRobotCost, obsidianRobotCost.first, geodeRobotCost.first).max()
    }

    data class State(
        var minute: Int = 1,
        var ore: Int = 0,
        var clay: Int = 0,
        var obsidian: Int = 0,
        var geode: Int = 0,
        var oreRobots: Int = 1,
        var clayRobots: Int = 0,
        var obsidianRobots: Int = 0,
        var geodeRobots: Int = 0,
    )

    private val cache = mutableMapOf<Pair<State, Blueprint>, State>()
    private var bestResult = mutableMapOf<Blueprint, Int>()

    private fun findBest(state: State, bp: Blueprint, depth: Int): State {
        // gather new resources
        val newResources = state.copy(
            minute = state.minute + 1,
            ore = min(state.ore + state.oreRobots, bp.maxOreCost * (depth - state.minute)),
            clay = min(state.clay + state.clayRobots, bp.obsidianRobotCost.second * (depth - state.minute)),
            obsidian = min(state.obsidian + state.obsidianRobots, bp.geodeRobotCost.second * (depth - state.minute)),
            geode = state.geode + state.geodeRobots,
        )

        // root case
        if (state.minute == depth) {
            return newResources.copy(minute = state.minute)
        }

        // always build a geode robot if possible
        if (state.ore >= bp.geodeRobotCost.first && state.obsidian >= bp.geodeRobotCost.second) {
            val newGeodeRobot = newResources.copy(
                ore = newResources.ore - bp.geodeRobotCost.first,
                obsidian = newResources.obsidian - bp.geodeRobotCost.second,
                geodeRobots = state.geodeRobots+ 1
            )

            val result = if (newGeodeRobot to bp in cache) cache[newGeodeRobot to bp]!!
                                else findBest(newGeodeRobot, bp, depth)

            cache[state to bp] = result
            bestResult[bp] = max(result.geode * result.geodeRobots * 2 * (depth - result.minute),
                bestResult.getOrDefault(bp, 0))
            return result
        }

        // otherwise, consider all options to build robots to skip "turn" to collect resources
        val states = mutableListOf(newResources)

        if (state.ore >= bp.obsidianRobotCost.first &&
            state.clay >= bp.obsidianRobotCost.second &&
            state.obsidianRobots < bp.geodeRobotCost.second)
        {
            states.add(newResources.copy(
                ore = newResources.ore - bp.obsidianRobotCost.first,
                clay = newResources.clay - bp.obsidianRobotCost.second,
                obsidianRobots = state.obsidianRobots + 1
            ))
        }

        if (state.ore >= bp.oreRobotCost && state.oreRobots < bp.maxOreCost) {
            states.add(newResources.copy(ore = newResources.ore - bp.oreRobotCost, oreRobots = state.oreRobots + 1))
        }

        if (state.ore >= bp.clayRobotCost && state.clayRobots < bp.obsidianRobotCost.second) {
            states.add(newResources.copy(ore = newResources.ore - bp.clayRobotCost, clayRobots= state.clayRobots + 1))
        }

        // remove states that do not have the potential to be higher than the current maximum
        states.removeIf {
            it.geode + state.geodeRobots * 2 * (depth - it.minute) < bestResult.getOrDefault(bp, 0)
        }

        // if no good states remain, prune tree
        if (states.isEmpty()) return state.copy(geode = -1)

        // find the best sub-state
        val best = states.map {
            if (state to bp in cache) cache[state to bp]!! else findBest(it, bp, depth)
        }.maxBy { it.geode }

        // update cache and best geode possible
        cache[state to bp] = best
        bestResult[bp] = max(best.geode * best.geodeRobots * 2 * (depth - best.minute), bestResult.getOrDefault(bp, 0))
        return best
    }

    override fun part1() : Any {
        return blueprints.sumOf { bp ->
            cache.clear()
            bp.id * findBest(State(), bp, 24).geode
        }
    }

    override fun part2() : Any {
        return blueprints.take(3).map { bp ->
            cache.clear()
            findBest(State(), bp, 32).geode
        }.product()
    }
}