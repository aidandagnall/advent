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
print(len([i for i in grid if grid[i] == 'black']))