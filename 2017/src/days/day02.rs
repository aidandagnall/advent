use itertools::Itertools;
use super::day::Day;
use crate::util::solution::Solution;

pub struct Day02;

impl Day for Day02 {
    fn part1(input: Vec<String>) -> Solution {
        let result = input.iter()
            .map(|line| {
                let nums: Vec<i32> = line.split_whitespace()
                    .map(|x| x.parse::<i32>().unwrap())
                    .sorted()
                    .collect();
                nums.last().unwrap() - nums.first().unwrap()
            })
            .sum();

        return Solution::I32(result);
    }

    fn part2(input: Vec<String>) -> Solution {
        let result = input.iter()
            .map(|line| line.split_whitespace().map(|x| x.parse::<i32>().unwrap()).collect::<Vec<i32>>())
            .map(|row|
                row.iter().cartesian_product(row.iter())
                    .filter(|(&a, &b)| a != b && a % b == 0)
                    .map(|(&a, &b)| a / b)
                    .collect::<Vec<i32>>()
                    .first()
                    .unwrap()
                    .to_owned()
            ).sum::<i32>();

        return Solution::I32(result);
    }
}
