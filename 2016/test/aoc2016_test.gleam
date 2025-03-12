import days/day01
import gleam/int
import gleam/io
import gleam/string
import gleeunit
import gleeunit/should
import simplifile

pub fn main() {
  gleeunit.main()
}

fn get_input(day: Int) -> List(String) {
  let file =
    simplifile.read(
      "inputs_test/day"
      <> string.pad_start(int.to_string(day), to: 2, with: "0")
      <> ".txt",
    )
  case file {
    Ok(content) -> content |> string.split(on: "\n")
    Error(err) ->
      io.debug(err)
      |> panic
  }
}

pub fn day_01_part_1_test() {
  let input = get_input(1)
  day01.day01.part1(input)
  |> should.equal(Ok(8))
}

pub fn day_01_part_2_test() {
  let input = get_input(1)
  day01.day01.part2(input)
  |> should.equal(Ok(4))
}
