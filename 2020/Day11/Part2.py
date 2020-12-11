import copy
def getvisible(arr, x, y):
    visible = []
    for i in range(y - 1, y + 2):
        if not 0 <= i < len(arr): continue
        for j in range(x - 1, x + 2):
            if not 0 <= j < len(arr[0]): continue
            if i == y and j == x: continue
            a, b = i, j
            while arr[a][b] == '.':
                a += i - y
                b += j - x
                if not a in range(len(arr)) or not b in range(len(arr[0])): break
            if not a in range(len(arr)) or not b in range(len(arr[0])): continue
            visible.append(arr[a][b])
    return visible

input = [[j for j in i.strip()] for i in open('input.txt')]
stable = 0
current = input
next = copy.deepcopy(current)
while (stable == 0):
    stable = 1
    for i in range(0, len(input)):
        for j in range(0, len(input[i])):
            if current[i][j] == '.': continue
            visible = getvisible(current, j,i)
            if current[i][j] == 'L' and visible.count('#') == 0:
                stable = 0
                next[i][j] = '#'
            if current[i][j] == '#' and visible.count('#') >= 5:
                stable = 0
                next[i][j] = 'L'
    current = copy.deepcopy(next)

print(sum(i.count('#') for i in current))
        