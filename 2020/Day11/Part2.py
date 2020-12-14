input = [[j for j in i.strip()] for i in open('input.txt')]
height, width = range(len(input)), range(len(input[0]))
def getadjacentcoords(arr, x ,y):
    adjacent = []
    for i in range(y - 1, y + 2):
        if not i in range(len(arr)): continue
        for j in range(x - 1, x + 2):
            if not j in range(len(arr[0])): continue
            if i == y and j == x: continue
            a,b = i,j
            while a+(i-y) in height and b+(j-x) in width and arr[a][b] == '.':
                a, b = a + (i-y), b + (j-x)
            adjacent.append((a,b))
    return adjacent

def runsimulation(arr):
    next = []
    for i, row in enumerate(arr):
        next.append([])
        for j, col in enumerate(row):
            adjacent = [arr[x][y] for x, y in neighbours[i][j]]
            if col == 'L' and adjacent.count('#') == 0: next[i].append('#')
            elif col == '#' and adjacent.count('#') >= 5: next[i].append('L')
            else: next[i].append(col)
    return next

neighbours = [[getadjacentcoords(input, j, i) for j in width] for i in height]
current = []
next = input

while current != next:
    current = next
    next = runsimulation(current)
print(sum(i.count('#') for i in next))