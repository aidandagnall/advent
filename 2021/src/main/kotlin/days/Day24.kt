package days

import java.lang.Integer.max
import java.lang.Integer.min

class Day24 : Day(24) {
//    15, 9           PUSH input[0] + 9
//    11, 1           PUSH input[1] + 1
//    10, 11          PUSH input[2] + 11
//    12, 3           PUSH input[3] + 3
//    -11, 10,        POP. input[4] == popped_value - 11
//    11, 5           PUSH input[5] + 5
//    14, 0,          PUSH input[6] + 0
//    -6, 7,          POP. input[7] == popped_value - 6
//    10, 9,          PUSH input[8] + 9
//    -6, 15,         POP. input[9] == popped_value - 6
//    -6, 4,          POP. input[10] == popped_value - 6
//    -16, 10,        POP. input[11] == popped_value - 16
//    -4, 4,          POP. input[12] == popped_value - 4
//    -2, 9,          POP. input[13] == popped_value - 2
//
//
//    input[4] = input[3] - 8  // 1, 9
//    input[7] = input[6] - 6  // 3, 9
//    input[9] = input[8] + 3  // 9, 6
//    input[10] = input[5] - 1 // 8, 9
//    input[11] = input[2] - 5 // 4, 9
//    input[12] = input[1] - 3 // 6, 9
//    input[13] = input[0] + 7 // 9 , 2
//
//    29991993698469

    override fun part1() : Any {
        return 29991993698469L
    }


//    input[4] = input[3] - 8  // 1, 9
//    input[7] = input[6] - 6  // 1, 7
//    input[9] = input[8] + 3  // 4, 1
//    input[10] = input[5] - 1 // 1, 2
//    input[11] = input[2] - 5 // 1, 6
//    input[12] = input[1] - 3 // 1, 4
//    input[13] = input[0] + 7 // 8 , 1
//
//    14691271141118

    override fun part2() : Any {
        return 14691271141118L
    }

}