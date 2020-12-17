from copy import deepcopy
from itertools import product

curr_gen = set()

def get_active_neighbours(x, y, z, w):
    neighbours = list(product(*[
        [x - 1, x, x + 1],
        [y - 1, y, y + 1],
        [z - 1, z, z + 1],
        [w - 1, w, w + 1]
    ]))
    return len([i for i in neighbours if i in curr_gen and i != (x, y, z, w)])

input = [i.strip() for i in open('input.txt', 'r')]
for x, line in enumerate(input):
    for y, char in enumerate(line):
        if char == '#': curr_gen.add((x, y, 0, 0))

for i in range(6):
    next_gen = deepcopy(curr_gen)

    grid_range = list(product(*[
        range(min(curr_gen, key=lambda x: x[0])[0] -1, max(curr_gen, key=lambda x: x[0])[0] + 2),
        range(min(curr_gen, key=lambda x: x[1])[1] -1, max(curr_gen, key=lambda x: x[1])[1] + 2),
        range(min(curr_gen, key=lambda x: x[2])[2] -1, max(curr_gen, key=lambda x: x[2])[2] + 2),
        range(min(curr_gen, key=lambda x: x[3])[3] -1, max(curr_gen, key=lambda x: x[3])[3] + 2)
    ]))

    for x, y, z, w in grid_range:
        n = get_active_neighbours(x, y, z, w)
        if (x, y, z, w) in next_gen and n not in [2, 3]:
            next_gen.remove((x, y, z, w))
        elif n == 3:
            next_gen.add((x, y, z, w))
    curr_gen = next_gen
print(len(curr_gen))