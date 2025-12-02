use super::day::Day;
use crate::util::solution::Solution;

pub struct Day01;

impl Day for Day01 {
    fn part1(input: Vec<String>) -> Solution {
        return solve(input.first().unwrap(), 1);
    }

    fn part2(input: Vec<String>) -> Solution {
        let first = input.first().unwrap();
        return solve(first, first.len() / 2);
    }
}

fn solve(input: &String, offset: usize) -> Solution {
    let result = input.chars()
        .zip(input.chars().cycle().skip(offset))
        .filter(|(x, y)| x == y)
        .map(|(x, _)| x.to_digit(10).unwrap_or(0))
        .sum::<u32>();

    return Solution::U32(result);
}
