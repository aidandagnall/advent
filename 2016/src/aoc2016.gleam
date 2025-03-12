import argv
import day
import days/day01
import gleam/int
import gleam/io
import gleam/list
import gleam/result
import gleam/string
import simplifile

const days: List(day.Day) = [day01.day01]

pub fn main() {
  use number <- result.try(get_day_number())
  use day <- result.try(get_day(number))

  case run(day) {
    Ok(#(p1, p2)) -> print_results(number, p1, p2) |> Ok()
    Error(err) -> io.debug(err) |> Error()
  }
}

fn print_results(day: Int, p1: Int, p2: Int) {
  io.print(
    "Running Day "
    <> int.to_string(day)
    <> "\n\tPart 1:"
    <> int.to_string(p1)
    <> "\n\tPart 2:"
    <> int.to_string(p2)
    <> "\n",
  )
}

fn get_day_number() {
  case argv.load().arguments {
    ["day", number] -> int.parse(number)
    _ -> Error(Nil)
  }
}

fn get_day(number: Int) -> Result(day.Day, Nil) {
  days
  |> list.filter(fn(day) { day.number == number })
  |> list.first()
}

fn get_input(day: Int) -> List(String) {
  let file =
    simplifile.read(
      "inputs/day"
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

fn run(day: day.Day) -> Result(#(Int, Int), Nil) {
  let input = get_input(day.number)

  use p1 <- result.try(day.part1(input))
  use p2 <- result.try(day.part2(input))
  Ok(#(p1, p2))
}
