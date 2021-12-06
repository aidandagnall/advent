package util
import days.Day
import org.reflections.Reflections

object Runner {

    private val reflections = Reflections("days")

    @JvmStatic
    fun main(args : Array<String>) {
        val solve : (Day) -> Unit = if ("-t" in args) Day::solveTimed else Day::solve
        if ("-m" in args) {
            (reflections.getSubTypesOf(Day::class.java)
                .sortedBy { it.simpleName.replace("Day", "").toInt() }
                .forEach { solve((it.constructors[0].newInstance() as Day)) })
        } else {
            if ("-l" in args) {
                solve(reflections.getSubTypesOf(Day::class.java)
                    .sortedBy { it.simpleName.replace("Day", "").toInt() }
                    .last()
                    .constructors[0].newInstance() as Day)
            } else {
                solve(reflections.getSubTypesOf(Day::class.java)
                    ?.find { it.simpleName.replace("Day", "").toInt() == args[0].toInt() }
                    ?.constructors?.get(0)?.newInstance() as Day)
            }

        }

    }

}