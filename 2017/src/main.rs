mod days;
mod util;
use crate::days::day::Day;
use days::*;
use util::solution::Solution;

fn main() {
    run(4);
}

fn run(day: u8) {
    let input = get_input(day);
    let (p1, p2) = get_day_solver(day);


    println!("Part 1: {}", p1(input.clone()));
    println!("Part 2: {}", p2(input.clone()));
}

fn get_day_solver(day: u8) -> (fn(Vec<String>) -> Solution, fn(Vec<String>) -> Solution) {
    match day {
         1 => (day01::Day01::part1, day01::Day01::part2),
         2 => (day02::Day02::part1, day02::Day02::part2),
         3 => (day03::Day03::part1, day03::Day03::part2),
         4 => (day04::Day04::part1, day04::Day04::part2),
         5 => (day05::Day05::part1, day05::Day05::part2),
         6 => (day06::Day06::part1, day06::Day06::part2),
         7 => (day07::Day07::part1, day07::Day07::part2),
         8 => (day08::Day08::part1, day08::Day08::part2),
         9 => (day09::Day09::part1, day09::Day09::part2),
         10 => (day10::Day10::part1, day10::Day10::part2),
         11 => (day11::Day11::part1, day11::Day11::part2),
         12 => (day12::Day12::part1, day12::Day12::part2),
         13 => (day13::Day13::part1, day13::Day13::part2),
         14 => (day14::Day14::part1, day14::Day14::part2),
         15 => (day15::Day15::part1, day15::Day15::part2),
         16 => (day16::Day16::part1, day16::Day16::part2),
         17 => (day17::Day17::part1, day17::Day17::part2),
         18 => (day18::Day18::part1, day18::Day18::part2),
         19 => (day19::Day19::part1, day19::Day19::part2),
         20 => (day20::Day20::part1, day20::Day20::part2),
         21 => (day21::Day21::part1, day21::Day21::part2),
         22 => (day22::Day22::part1, day22::Day22::part2),
         23 => (day23::Day23::part1, day23::Day23::part2),
         24 => (day24::Day24::part1, day24::Day24::part2),
         25 => (day25::Day25::part1, day25::Day25::part2),
         _ => unimplemented!(),
    }
}

fn get_input(day: u8) -> Vec<String> {
    return match day {
        1 => include_str!("../inputs/day01.txt"),
        2 => include_str!("../inputs/day02.txt"),
        3 => include_str!("../inputs/day03.txt"),
        4 => include_str!("../inputs/day04.txt"),
        5 => include_str!("../inputs/day05.txt"),
        6 => include_str!("../inputs/day06.txt"),
        7 => include_str!("../inputs/day07.txt"),
        8 => include_str!("../inputs/day08.txt"),
        9 => include_str!("../inputs/day09.txt"),
        10 => include_str!("../inputs/day10.txt"),
        11 => include_str!("../inputs/day11.txt"),
        12 => include_str!("../inputs/day12.txt"),
        13 => include_str!("../inputs/day13.txt"),
        14 => include_str!("../inputs/day14.txt"),
        15 => include_str!("../inputs/day15.txt"),
        16 => include_str!("../inputs/day16.txt"),
        17 => include_str!("../inputs/day17.txt"),
        18 => include_str!("../inputs/day18.txt"),
        19 => include_str!("../inputs/day19.txt"),
        20 => include_str!("../inputs/day20.txt"),
        21 => include_str!("../inputs/day21.txt"),
        22 => include_str!("../inputs/day22.txt"),
        23 => include_str!("../inputs/day23.txt"),
        24 => include_str!("../inputs/day24.txt"),
        25 => include_str!("../inputs/day25.txt"),
        _ => unimplemented!(),
    }.lines()
        .map(|x| x.to_string())
        .collect();
}
