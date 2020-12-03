area = [[j for j in i.strip()] for i in open('input.txt')]
trees = 0
for y in range(0, len(area)):
    if area[y][(3 * y) % len(area[0])] == '#':
        trees +=1
print(trees)