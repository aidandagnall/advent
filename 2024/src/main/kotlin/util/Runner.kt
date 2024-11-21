package util
import days.Day
import org.reflections.Reflections
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

object Runner {

    private val reflections = Reflections("days")

    @JvmStatic
    fun main(args : Array<String>) {
        val solve = if ("-t" in args) Class<out Day>::runTimed else Class<out Day>::run
        val days = reflections.getSubTypesOf(Day::class.java)

        when {
            "-m" in args -> days.sortedBy { it.simpleName.replace("Day", "").toInt() }
                                .filter { it.simpleName.replace("Day", "").toInt() != 0 }
                                .forEach { solve(it) }

            "-l" in args -> solve(days.maxByOrNull { it.simpleName.replace("Day", "").toInt() }!!)
            else ->  days.find { it.simpleName.replace("Day", "").toInt() == args[0].toInt() }
                                ?.let { solve(it) }
        }
    }
}

@OptIn(ExperimentalTime::class)
private fun Class<out Day>.runTimed() {
    val (_, total) = measureTimedValue {
        val (day, time) = measureTimedValue { constructors[0].newInstance() as Day }
        println("Day: ${day.day}")
        println("\tInit: ${time.toString(DurationUnit.SECONDS, 4)}")
        (Day::solveTimed)((constructors[0].newInstance() as Day))
    }
    println()
    println("\tTotal: ${total.toString(DurationUnit.SECONDS, 4)}")
}
private fun Class<out Day>.run() {
    (Day::solve)((constructors[0].newInstance() as Day))
}
