use crate::util::solution::Solution;

pub trait Day {
    fn part1(input: Vec<String>) -> Solution;
    fn part2(input: Vec<String>) -> Solution;
}
