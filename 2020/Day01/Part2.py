from itertools import combinations

input = [int(i) for i in open('input.txt')]
for i, j, k in combinations(input, 3):
    if i + j + k== 2020:
        print(f'{i} * {j} * {k} = {i*j*k}')
        break