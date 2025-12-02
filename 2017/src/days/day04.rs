use itertools::Itertools;
use std::collections::HashSet;

use super::day::Day;
use crate::util::solution::Solution;

pub struct Day04;

impl Day for Day04 {
    fn part1(input: Vec<String>) -> Solution {
        let result = input
            .iter()
            .filter(|&line| {
                let words = line.split_whitespace();
                let unique_words: HashSet<&str> = HashSet::from_iter(words.clone());
                words.count() == unique_words.len()
            })
            .count();

        return Solution::I32(result.try_into().unwrap());
    }

    fn part2(input: Vec<String>) -> Solution {
        let result = input
            .iter()
            .filter(|&line| {
                let words = line.split_whitespace();
                let unique_words: HashSet<&str> = HashSet::from_iter(words.clone());
                let sorted_words: HashSet<String> =
                    HashSet::from_iter(words.clone().map(|w| w.chars().sorted().join("")));
                words.clone().count() == unique_words.len() && words.count() == sorted_words.len()
            })
            .count();

        return Solution::I32(result.try_into().unwrap());
    }
}
