from math import prod

buses = []
with open('input.txt', 'r') as f:
    f.readline()
    for offset, id in enumerate(f.readline().strip().split(',')):
        if id == 'x': continue
        buses.append((int(id), offset))

N = prod([id for id,_ in buses])
m = sum([(id - offset) * N // id * pow(N // id, -1, id) for id, offset in buses]) % N

print(m)