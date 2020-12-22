from collections import deque
from copy import deepcopy
from itertools import islice
def play(p1, p2):
    states = []
    while len(p1) > 0 and len(p2) > 0:
        if (list(p1), list(p2)) not in states:
            states.append((list(p1) ,list(p2)))
        else:
            return 1
        p1card = p1.popleft()
        p2card = p2.popleft()
        
        if p1card <= len(p1) and p2card <= len(p2):
            newp1 = deque(islice(deepcopy(p1), 0, p1card))
            newp2 = deque(islice(deepcopy(p2), 0, p2card))
            res = play(newp1, newp2)
            if res == 1:
                p1.append(p1card)
                p1.append(p2card)
            else:
                p2.append(p2card)
                p2.append(p1card)
        else:
            if p1card > p2card:
                p1.append(p1card)
                p1.append(p2card)
            else:
                p2.append(p2card)
                p2.append(p1card)
    if len(p1) == 0: return 2
    else: return 1

input = [i.strip() for i in open('input.txt')]
p1 = deque()
p2 = deque()
for line in input[1:input.index('')]:
    p1.append(int(line))
for line in input[input.index('Player 2:') + 1 :]:
    p2.append(int(line))

play(p1, p2)

deck = p1 if len(p1) > 0 else p2
sum = 0
for i in range(1, len(deck) + 1):
    sum += i * deck.pop()
print(sum)