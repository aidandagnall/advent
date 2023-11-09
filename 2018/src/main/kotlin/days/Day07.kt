package days

class Day07 : Day(7) {

    data class Step(val value: String, val next: MutableList<Step> = mutableListOf(), val reqs: MutableList<Step> = mutableListOf()) {
        override fun toString(): String = value
        override fun hashCode(): Int = value.first().code
        override fun equals(other: Any?): Boolean = other is Step && value == other.value
    }

    data class Worker(var timeLeft: Int = 0, var task: Step? = null)

    private val regex = Regex("""Step ([A-Z]) must be finished before step ([A-Z]) can begin.""")
    private val starts = buildMap<String, Step> {
        inputList.map {
            regex.matchEntire(it)!!.destructured.also { (a, b) ->
                if (a !in this) this[a] = Step(a)
                if (b !in this) this[b] = Step(b)

                this[a]!!.next.add(this[b]!!)
                this[b]!!.reqs.add(this[a]!!)
            }
        }
    }.values.filter { it.reqs.isEmpty() }

    override fun part1() : Any = run(1, 0).first

    override fun part2() : Any = run(5, 60).second

    fun run(workerCount: Int, baseTime: Int): Pair<String, Int> {
        var time = 0
        val workers = List(workerCount) { Worker() }
        val next = starts.toMutableSet()
        val visited = mutableListOf<Step>()

        fun Worker.finishTask() {
            if (timeLeft > 0 || task == null) return
            visited.add(task!!)
            next.addAll(task!!.next)
            task = null
        }

        fun Worker.newTask() {
            if (timeLeft > 0) return
            val node = next.filter { it.reqs.all { it in visited } }.minByOrNull { it.value } ?: return
            next.removeIf { it.value == node.value }

            task = node
            timeLeft = baseTime + node.value.first().code - 'A'.code + 1
        }

        while (next.isNotEmpty() || visited.isEmpty() || workers.any { it.timeLeft > 0 }) {
            val timePassed = workers.filter { it.timeLeft > 0 }.minOfOrNull { it.timeLeft } ?: 0
            workers.forEach { it.timeLeft -= timePassed }
            time += timePassed

            workers.forEach(Worker::finishTask)
            workers.forEach(Worker::newTask)
        }

        return visited.joinToString("") { it.value} to time
    }
}