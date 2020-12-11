import copy
def getadjacent(arr, x, y):
    adjacent = []
    for i in range(y - 1, y + 2):
        if not 0 <= i < len(arr): continue
        for j in range(x - 1, x + 2):
            if not 0 <= j < len(arr[0]): continue
            if i == y and j == x: continue
            adjacent.append(arr[i][j])
    return adjacent

input = [[j for j in i.strip()] for i in open('input.txt')]
stable = False
current = input
next = copy.deepcopy(current)
while not stable:
    stable = True
    for i in current:
        print(''.join(i).strip())
    print()

    for i in range(0, len(input)):
        for j in range(0, len(input[i])):
            if current[i][j] == '.': continue
            adjacent = getadjacent(current, j,i)
            if current[i][j] == 'L' and adjacent.count('#') == 0:
                stable = False
                next[i][j] = '#'
            if current[i][j] == '#' and adjacent.count('#') >= 4:
                stable = False
                next[i][j] = 'L'
    current = copy.deepcopy(next)

print(sum(i.count('#') for i in current))
        