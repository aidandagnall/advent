from itertools import combinations

input = [int(i) for i in open('input.txt')]
for i, j in combinations(input, 2):
    if i + j == 2020:
        print(f'{i} * {j} = {i*j}')
        break



