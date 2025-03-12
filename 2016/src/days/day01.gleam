import day
import gleam/int
import gleam/io
import gleam/list
import gleam/result
import gleam/set
import gleam/string
import utils/direction.{type Position, type Turn}

pub const day01 = day.Day(1, part1, part2)

pub type Command =
  #(Turn, Int)

fn part1(input: List(String)) -> Result(Int, Nil) {
  parse_input(input)
  |> find_all_positions()
  |> list.last()
  |> result.map(fn(x) { get_manhattan_distance(x) })
}

fn part2(input: List(String)) -> Result(Int, Nil) {
  parse_input(input)
  |> find_all_positions()
  |> find_first_duplicate()
  |> result.map(fn(x) { get_manhattan_distance(x) })
}

fn discard_error(a: Result(ok, error)) -> ok {
  case a {
    Ok(value) -> value
    Error(err) -> io.debug(err) |> panic
  }
}

fn parse_input(input: List(String)) -> List(Command) {
  list.first(input)
  |> discard_error()
  |> string.split(", ")
  |> list.map(parse_step)
}

fn parse_step(instruction: String) -> Command {
  let #(dir, num) = case instruction {
    "R" <> num -> #(direction.RIGHT, num)
    "L" <> num -> #(direction.LEFT, num)
    _ -> io.debug("Error parsing " <> instruction) |> panic
  }
  case int.parse(num) {
    Ok(n) -> #(dir, n)
    Error(err) -> io.debug(err) |> panic
  }
}

fn find_all_positions(commands: List(Command)) -> List(Position) {
  let #(_, positions) =
    list.map_fold(commands, #(0, 0, direction.NORTH), fn(acc, i) {
      let #(t, magnitude) = i
      let #(x, y, dir) = acc
      let new_dir = direction.turn(dir, t)
      let positions =
        list.range(1, magnitude)
        |> list.map(fn(it) {
          let #(xn, yn, _) = direction.move(#(x, y, new_dir), it)
          #(xn, yn)
        })
      #(direction.move(#(x, y, new_dir), magnitude), positions)
    })
  list.flatten(positions)
}

fn find_first_duplicate(positions: List(Position)) -> Result(#(Int, Int), Nil) {
  let search =
    positions
    |> list.fold(Ok(set.new()), fn(acc, it) {
      case acc {
        Ok(s) -> {
          case set.contains(s, it) {
            False -> Ok(set.insert(s, it))
            True -> Error(it)
          }
        }
        Error(s) -> Error(s)
      }
    })

  case search {
    Error(found) -> Ok(found)
    Ok(_) -> Error(Nil)
  }
}

fn get_manhattan_distance(in: Position) -> Int {
  let #(x, y) = in
  int.absolute_value(x) + int.absolute_value(y)
}
