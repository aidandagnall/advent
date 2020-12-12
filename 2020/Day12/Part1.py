input = [(i[0], int(i[1:])) for i in open('input.txt')]
pos = [0,0]
dir = 1
dirs = [[0,1], [1, 0], [0, -1], [-1, 0]]

for instruction, value in input:
    if instruction == 'N':
        pos[1] += value
    elif instruction == 'S':
        pos[1] -= value
    elif instruction == 'E':
        pos[0] += value
    elif instruction == 'W':
        pos[0] -= value
    elif instruction == 'L':
        dir = (dir - int(value / 90)) % 4
    elif instruction == 'R':
        dir = (dir + int(value / 90)) % 4
    elif instruction == 'F':
        pos[0] += dirs[dir][0] * value
        pos[1] += dirs[dir][1] * value
print(f'x={pos[0]} y={pos[1]} dist={abs(pos[0]) + abs(pos[1])} ')