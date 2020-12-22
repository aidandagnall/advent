import re
from copy import deepcopy
from math import prod
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

found = []
for t in tiles:
    no_link = 0
    for b in tiles[t].borders:
        if borders[b] == 1:
            no_link += 1
    # if tile has two unmatches edges, it is a corner
    if no_link == 2:
        found.append(t)
print(prod(found))