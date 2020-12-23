import re
from copy import deepcopy
class Tile:
    def __init__(self, tile, number):
        self.tile = tile
        self.number = number
        self.borders = [tile[0]]
        self.borders.append(''.join([i[0] for i in tile]))
        self.borders.append(''.join([i[-1] for i in tile]))
        self.borders.append(tile[-1])
        for i in self.borders:
            if i[::-1] not in borders:
                borders[i[::-1]] = 0
            borders[i[::-1]] += 1
            if i not in borders:
                borders[i] = 0
            borders[i] += 1
    def rotate(self):
        self.tile = list(zip(*self.tile[::-1]))
        self.tile = [''.join(i) for i in self.tile]
    def flip(self):
        self.tile = [i[::-1] for i in self.tile]
    def get_left(self):
        return ''.join([i[0] for i in self.tile])
    def get_right(self):
        return ''.join([i[-1] for i in self.tile])
    def get_top(self):
        return self.tile[0]
    def get_bottom(self):
        return self.tile[-1]

borders = {}
tiles = {}
with open('input.txt') as f:
    tile = []
    tilenum = [int(i) for i in re.findall('[0-9]+', f.readline().strip())][0]
    for line in f:
        if line.strip() == '':
            tiles[tilenum] = Tile(deepcopy(tile), tilenum)
            tile = []
        elif 'Tile' in line:
            tilenum = [int(i) for i in re.findall('[0-9]+', line.strip())][0]
        else:
            tile.append(line.strip())
tiles[tilenum] = Tile(deepcopy(tile), tilenum)

# find corners
found = []
for t in tiles:
    no_link = 0
    for b in tiles[t].borders:
        if borders[b] == 1:
            no_link += 1
    if no_link == 2:
        found.append(tiles[t])
grid = [[None for i in range(12)] for i in range(12)]

# rotate initial corner correctly
grid[0][0] = found[0]
rotations = 0
while borders[found[0].get_left()] != 1 or borders[found[0].get_top()] != 1:
    if rotations > 3:
        found[0].flip()
        rotations = 0
    found[0].rotate()
    rotations += 1

# place all tiles
available_tiles = deepcopy(tiles)
available_tiles.pop(found[0].number)
for i, row in enumerate(grid):
    for j, t in enumerate(row):
        if (i, j) == (0, 0): continue
        if j > 0:
            prev = grid[i][j - 1]
            prevBorder = prev.get_right()
        else:
            prev = grid[i - 1][j]
            prevBorder = prev.get_bottom()

        match = None
        for tile in available_tiles:
            if tiles[tile] == prev: continue
            if j == 0: currBorder = tiles[tile].get_left()
            else:      currBorder = tiles[tile].get_top()
            if prevBorder in tiles[tile.borders] or prevBorder[::-1] in tiles[tile].borders:
                rotations = 0
                while prevBorder != currBorder:
                    if rotations > 3:
                        tiles[tile].flip()
                        rotations = 0
                        continue
                    tiles[tile].rotate()
                    rotations += 1
                grid[i][j] = tiles[tile]
                match = tile
                break
        available_tiles.pop(match)

# convert to single 2D list
full = []
for i, row in enumerate(grid):
    for i in range(1, 10 - 1):
        full.append(''.join([j.tile[i][1:-1] for j in row]))

# find all occurences of the monster pattern
contains_monster = False
monster_count = 0
rotations = 0
while monster_count == 0:
    if rotations > 3:
        full = [i[::-1] for i in full]
        rotations = 0
    for row in range(len(full) - 2):
        for col in range(18, len(full[0]) - 1):
            monster = [
                full[row][col], full[row + 1][col + 1], full[row + 1][col], full[row + 1][col - 1],
                full[row + 2][col - 2], full[row + 2][col - 5], full[row + 1][col - 6], full[row + 1][col - 7],
                full[row + 2][col - 8], full[row + 2][col - 11], full[row + 1][col - 12], full[row + 1][col - 13],
                full[row + 2][col - 14], full[row + 2][col - 17], full[row + 1][col - 18]
            ]
            if ''.join(monster) == len(monster) * '#':
                monster_count += 1
    if monster_count > 0: break
    full = list(zip(*full[::-1]))
    full = [''.join(i) for i in full]
    rotations += 1

count = sum([i.count('#') for i in full])
print(count - (monster_count * 15))