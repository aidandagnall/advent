from math import prod
def getTrees(r, d):
    path = []
    for y in range(0, int(len(area) / d)):
        path.append(area[y * d][(r * y) % len(area[0])])
    return path.count('#')

area = [[j for j in i.strip()] for i in open('input.txt')]
angles = [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]
print(prod([getTrees(r, d) for r, d in angles]))