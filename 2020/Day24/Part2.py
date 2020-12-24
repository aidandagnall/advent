from copy import deepcopy

def e(p):
    return (p[0] - 2, p[1])
def w(p):
    return (p[0] + 2, p[1])
def ne(p):
    return(p[0] - 1, p[1] - 1)
def nw(p):
    return(p[0] + 1, p[1] - 1)
def se(p):
    return (p[0] - 1, p[1] + 1)
def sw(p):
    return (p[0] + 1, p[1] + 1)
def neighbours(p):
    n = [
        e(p), w(p), ne(p), nw(p), se(p), sw(p)
    ]
    for i in n:
        if i not in grid:
            grid[i] = 'white'
    return [grid[i] for i in n]

def flip(p, g):
    g[p] = ('black' if g[p] == 'white' else 'white')

input = [i.strip() for i in open('input.txt')]
grid = {}
for line in input:
    p = (0, 0)
    dir = ''
    for char in line:
        if char in ['n', 's']:
            dir = char
            continue
        if dir + char == 'e':       p = e(p)
        elif dir + char == 'w':     p = w(p)
        elif dir + char == 'ne':    p = ne(p)
        elif dir + char == 'nw':    p = nw(p)
        elif dir + char == 'se':    p = se(p)
        elif dir + char == 'sw':    p = sw(p)
        dir = ''
        
    if p not in grid:
        grid[p] = 'white'
    flip(p, grid)

days = 100
grid_cpy = None
for i in range(1, days + 1):
    grid_cpy = deepcopy(grid)
    minX = min(grid)[0] - 1
    maxX = max(grid)[0] + 1
    minY = min(grid, key = lambda t: t[1])[1] - 1
    maxY = max(grid, key = lambda t: t[1])[1] + 1
    for row in range(minX, maxX + 1):
        for col in range(minY, maxY + 1):
            if (row + col) % 2 != 0 : continue
            if (row, col) not in grid_cpy:
                grid_cpy[(row,col)] = 'white'
            n = neighbours((row, col))
            count = n.count('black')
            if grid_cpy[(row, col)] == 'black':
                if count == 0 or count > 2:
                    flip((row, col), grid_cpy)
            else:
                if count == 2:
                    flip((row, col), grid_cpy)

    grid = grid_cpy
    c = len([i for i in grid if grid[i] == 'black'])
    print(f'--- count on day {i} : {c} ---')