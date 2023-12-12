package days

import kotlin.math.max

class Day12 : Day(12) {

    val records = inputList.map {
        it.split(" ").let { (gears, groups) ->
            gears to groups.split(",").filter { it.isNotEmpty() }.map { it.toInt() }
        }
    }

    override fun part1() : Any {
        return records.sumOf { (gears, groups) ->
            cache.clear()
            println("$gears -> $groups")
            find(gears, groups, gears.count { it == '?' }).also { println(cache) }.also { println(" RESULT: $gears -> $it") }
        }
    }

    val cache = mutableMapOf<Pair<List<String>,List<Int>>, Int>()

    private fun find(gears: String, groups: List<Int>, count: Int): Int {
        if (count == 0) {
            if (!gears.satisfies(groups)) {
                return 0
            }
//            println("  $gears satisfies $groups")
            return 1
        }

        val firstQ = gears.indexOfFirst { it == '?' }

        val gearsRemaining = gears.split(".").filter { it.isNotEmpty() }.dropWhile { '?' !in it }
//        val gearsRemaining = gears.substring(gears.indexOfFirst { it == '?' } + 1).split(".").filter { it.isNotEmpty() }
        val gearGroups = gears.split(".").filter { it.isNotEmpty() }
        val groupsRemaining = groups.withIndex().dropWhile {
            if (it.index >= gearGroups.size) false
            else gearGroups[it.index].count { it == '#' } == it.value && '?' !in gearGroups[it.index]
        }.map { it.value }
//            gears.split(".").filter { it.isNotEmpty() }
//            .zip(groups).dropWhile { (a, b) ->
//                a.count { it == '#' } == b && '?' !in a
//            }.map { it.second }
        if (gearsRemaining.isNotEmpty() && groupsRemaining.isNotEmpty()) {
            if (gearsRemaining.first().count { it == '#' } > groupsRemaining.first()) {
                return 0
            }
        }


//            groups.drop(
//            gears.substring(0, firstQ + 1).split(".").count { it.isNotEmpty() })

//        if (!gears.satisfies(groups, true) ){
//            cache[gearsRemaining to groupsRemaining] = 0
//            return 0
//        }


        println("""
            -------------------
            $gears -> $groups
            $gearsRemaining -> $groupsRemaining
        """.trimIndent())
        if(gearsRemaining to groupsRemaining in cache) {
            return cache[gearsRemaining to groupsRemaining]!!
        }

        val nextHash = gears.replaceFirst("?", "#")
        val nextDot = gears.replaceFirst("?", ".")

        val sum = find(nextHash, groups, count - 1) + find(nextDot, groups, count - 1)
        cache[gearsRemaining to groupsRemaining] = sum
        return sum
    }

    private fun String.satisfies(groups: List<Int>, loose: Boolean = false): Boolean {
        val gearGroups = split(".").filter { it.isNotEmpty() }

//        println("    $gearGroups -> $groups")

        if (!loose && groups.size != gearGroups.size) return false

        val t = gearGroups.zip(groups).all { (a, b) ->
                a.count { it == '#' } == b
            }

//        println("    $gearGroups -> $groups = $t")
        return t
    }

    override fun part2() : Any {
        return records.mapIndexed { i, (gears, groups) ->
            println("$i / ${records.count()}")
            val newGears = (1..5).joinToString("?") { gears }
            val newGroups = (1..5).flatMap { groups }
            println("$newGears, $newGroups")
            find(newGears, newGroups, newGears.count { it == '?' })
        }.sum().also { println(cache) }
    }
}