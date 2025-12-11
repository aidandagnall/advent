package days

class Day02 : Day(2) {
  val twice = """^(\d+)\1$""".toRegex()
  val nTimes = """^(\d+)\1+$""".toRegex()
  val ids = inputList.first().split(",")
    .map { it.split("-") }
    .flatMap { (a, b) -> a.toLong()..b.toLong() }
    .map { it.toString() }

  override fun part1(): Any = ids.filter { twice.matches(it) }.sumOf { it.toLong() }
  override fun part2(): Any = ids.filter { nTimes.matches(it) }.sumOf { it.toLong() }
}