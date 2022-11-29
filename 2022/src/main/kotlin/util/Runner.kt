package util
import days.Day
import org.reflections.Reflections

object Runner {

    private val reflections = Reflections("days")

    @JvmStatic
    fun main(args : Array<String>) {
        val solve : (Day) -> Unit = if ("-t" in args) Day::solveTimed else Day::solve
        val days = reflections.getSubTypesOf(Day::class.java)

        when {
            "-m" in args -> days.sortedBy { it.simpleName.replace("Day", "").toInt() }
                                .forEach { solve((it.constructors[0].newInstance() as Day)) }

            "-l" in args -> days.maxByOrNull { it.simpleName.replace("Day", "").toInt() }!!
                                .constructors[0].newInstance()
                                .let { solve(it as Day) }
            else ->  days.find { it.simpleName.replace("Day", "").toInt() == args[0].toInt() }
                                ?.constructors?.get(0)?.newInstance()
                                ?.let { solve(it as Day) }
        }
    }
}