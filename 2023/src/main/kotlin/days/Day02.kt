package days

class Day02 : Day(2) {
    data class Game(val number: Int, val red: Int, val green: Int, val blue: Int)
    private val requirements = inputList.map { line -> Game(
        """Game (\d+):""".toRegex().find(line)!!.destructured.component1().toInt(),
        """(\d+) red""".toRegex().findAll(line).maxOf { it.destructured.component1().toInt() },
        """(\d+) green""".toRegex().findAll(line).maxOf { it.destructured.component1().toInt() },
        """(\d+) blue""".toRegex().findAll(line).maxOf { it.destructured.component1().toInt() },
    ) }
    override fun part1() : Any = requirements.filter { it.red <= 12 && it.green <= 13 && it.blue <= 14 }.sumOf { it.number }
    override fun part2() : Any = requirements.sumOf { it.red * it.green * it.blue }
}