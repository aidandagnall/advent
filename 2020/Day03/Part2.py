area = [[j for j in i.strip()] for i in open('input.txt')]
totaltrees = 1
angles = [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]
for r, d in angles:
    trees = 0
    for y in range(0, int(len(area) / d)):
        if area[y * d][(r * y) % len(area[0])] == '#':
            trees +=1
    totaltrees *= trees
print(totaltrees)