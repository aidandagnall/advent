area = [[j for j in i.strip()] for i in open('input.txt')]
path = []
for y in range(0, len(area)):
    path.append(area[y][(3 * y) % len(area[0])])
print(path.count('#'))