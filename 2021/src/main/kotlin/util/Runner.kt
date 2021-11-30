package util
import days.Day
import org.reflections.Reflections

object Runner {

    private val reflections = Reflections("days")

    @JvmStatic
    fun main(args : Array<String>) {
        if (args.isEmpty()) {
            reflections.getSubTypesOf(Day::class.java)
                .sortedBy { it.simpleName.replace("Day", "").toInt() }
                .forEach { (it.constructors[0].newInstance() as Day).solve() }
        } else {
            val d = reflections.getSubTypesOf(Day::class.java)
                ?.find { it.simpleName.replace("Day", "").toInt() == args[0].toInt() }
                ?.constructors?.get(0)?.newInstance() as Day
            d.solve()
        }
    }

}