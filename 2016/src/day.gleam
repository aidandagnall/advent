pub type Day {
  Day(
    number: Int,
    part1: fn(List(String)) -> Result(Int, Nil),
    part2: fn(List(String)) -> Result(Int, Nil),
  )
}
