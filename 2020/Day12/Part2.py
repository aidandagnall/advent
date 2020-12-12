input = [(i[0], int(i[1:])) for i in open('input.txt')]
pos = [0,0]
waypoint = [10, 1]

def rotate(arr, n):
    if n == 0: return arr
    a, b = -arr[0], arr[1]
    return rotate([b, a], n - 1)

for instruction, value in input:
    if instruction == 'N':
        waypoint[1] += value
    elif instruction == 'S':
        waypoint[1] -= value
    elif instruction == 'E':
        waypoint[0] += value
    elif instruction == 'W':
        waypoint[0] -= value
    elif instruction == 'L':
        waypoint = rotate(waypoint, 4 - int(value / 90))
    elif instruction == 'R':
        waypoint = rotate(waypoint, int(value / 90))
    elif instruction == 'F':
        pos[0] += waypoint[0] * value
        pos[1] += waypoint[1] * value
print(f'x={pos[0]} y={pos[1]} dist={abs(pos[0]) + abs(pos[1])} ')