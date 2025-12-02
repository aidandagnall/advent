package util
import days.Day
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.time.DurationUnit
import kotlin.time.measureTimedValue

object RunDay {
    @JvmStatic
    fun main(args: Array<String>) =
        Day::class.sealedSubclasses
            .find { it.getNumber() == args[0].toInt() }
                ?.run() ?: error("No class found for Day ${args[0]}")
}

object RunMonth {
    @JvmStatic
    fun main(args: Array<String>) =
        Day::class.sealedSubclasses
            .sortedBy { it.getNumber() }
            .forEach { it.run() }
}

object RunLatest {
    @JvmStatic
    fun main(args: Array<String>) =
        Day::class.sealedSubclasses
            .maxBy { it.getNumber() }
            .run()
}

private fun KClass<out Day>.run() {
    val (day, initTime) = measureTimedValue { primaryConstructor?.call() ?: error("Primary constructor not present in day") }
    println("Day: ${day.number}")
    println("\tInit: ${initTime.toString(DurationUnit.SECONDS, 4)}")
    val solveTime = day.solveTimed()
    println()
    println("\tTotal: ${(initTime + solveTime).toString(DurationUnit.SECONDS, 4)}")
}

private fun KClass<out Day>.getNumber() = simpleName?.replace("Day", "")?.toInt() ?: error("Class has no name")