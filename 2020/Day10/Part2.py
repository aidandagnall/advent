input = [0] + sorted([int(i) for i in open('input.txt', 'r')])
input.append(max(input)+3)
diffs = [sum([i + j in input for j in [1,2,3]]) for i in input]
permutations = 1
patterns = [
    {'len':3, 'arr':[3,3,2], 'perm':7},
    {'len':2, 'arr':[3,2],   'perm':4},
    {'len':1, 'arr':[2],     'perm':2},
    {'len':1, 'arr':[1],     'perm':1},
]

i = 0
while(i < len(diffs) - 1):
    for p in patterns:
        if diffs[i:i + p['len']] == p['arr']:
            permutations *= p['perm']
            i += p['len']
            break;

print(permutations)