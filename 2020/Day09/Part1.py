from itertools import combinations
input = [int(i) for i in open('input.txt', 'r')]

preamble = 25
for i in range(preamble, len(input)):
    if input[i] not in [x + y for x,y in combinations(input[i-preamble:i], 2)]:
        print(f'Invalid number found at line:{i} value:{input[i]}')
        break