use std::collections::HashMap;

use super::day::Day;
use crate::util::solution::Solution;

pub struct Day03;

impl Day for Day03 {
    fn part1(input: Vec<String>) -> Solution {
        let start = input.first().unwrap().parse::<i32>().unwrap();
        let mut seen = HashMap::new();
        seen.insert((0, 0), 1);

        let mut direction = (1, 0);
        let mut n = (0, 0);

        while seen.get(&n).unwrap() < &start {
            let prev = seen.get(&n).unwrap();
            n = add(n, direction);

            if !seen.contains_key(&add(n, turn_left(direction))) {
                direction = turn_left(direction);
            }

            seen.insert(n, prev + 1);
        }

        let (x, y) = n;
        return Solution::I32(i32::abs(x) + i32::abs(y));
    }

    fn part2(input: Vec<String>) -> Solution {
        let start = input.first().unwrap().parse::<i32>().unwrap();
        let mut seen = HashMap::new();
        seen.insert((0, 0), 1);

        let mut direction = (1, 0);
        let mut n = (0, 0);

        while seen.get(&n).unwrap() < &start {
            n = add(n, direction);

            if !seen.contains_key(&add(n, turn_left(direction))) {
                direction = turn_left(direction);
            }

            let sum = neighbours(n)
                .iter()
                .map(|&a| seen.get(&a).unwrap_or(&0))
                .sum();

            seen.insert(n, sum);
        }

        return Solution::I32(*seen.get(&n).unwrap());
    }
}

fn turn_left(dir: (i32, i32)) -> (i32, i32) {
    match dir {
        (1, 0) => (0, -1),
        (0, -1) => (-1, 0),
        (-1, 0) => (0, 1),
        (0, 1) => (1, 0),
        _ => unreachable!()
    }
}

fn add(a: (i32, i32), b: (i32, i32)) -> (i32, i32) {
    let (x1, y1) = a;
    let (x2, y2) = b;
    return (x1 + x2, y1 + y2);
}

fn neighbours(a: (i32, i32)) -> Vec<(i32, i32)> {
    return vec!(-1, 0, 1).iter().flat_map(|&x|
        vec!(-1, 0, 1).iter().map(|&y| {
            add(a, (x, y))
        }).filter(|&b| a != b ).collect::<Vec<(i32,i32)>>()
    ).collect()
}
