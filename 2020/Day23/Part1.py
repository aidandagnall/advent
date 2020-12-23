# make move with given list of cups and current cup
def play(cups, curr):
    # get list of cups to be moved
    removed = (cups[(curr + 1) % len(cups) :])[0:3]
    if len(removed) < 3:
        for i in range(3 - len(removed)):
            removed.append(cups[i])
    
    # get destination cup
    dest = cups[curr] - 1
    while dest in removed:
        dest -= 1
    if dest == 0:
        dest = max([i for i in cups if i not in removed])
    dest = cups.index(dest)

    # store value of the current cup BEFORE the list changes
    currVal = cups[curr]

    # create new list of cups after move has been made
    rt = [i for i in cups[:dest + 1] if i not in removed] + removed + [i for i in cups[dest + 1:] if i not in removed]
    
    # get the next current cup
    curr = (rt.index(currVal) + 1) % len(cups)
    return rt, curr

input = [i.strip() for i in open('input.txt')][0]
cups = [int(c) for c in input]
curr = 0
for i in range(1, 101):
    cups, curr = play(cups, curr)
print(''.join([str(i) for i in cups]))