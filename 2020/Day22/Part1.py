from typing import Deque

input = [i.strip() for i in open('input.txt')]
p1 = Deque()
p2 = Deque()
for line in input[1:input.index('')]:
    p1.append(int(line))
for line in input[input.index('Player 2:') + 1 :]:
    p2.append(int(line))


while len(p1) > 0 and len(p2) > 0:
    p1card = p1.popleft()
    p2card = p2.popleft()
    if p1card > p2card:
        p1.append(p1card)
        p1.append(p2card)
    else:
        p2.append(p2card)
        p2.append(p1card)

deck = p1 if len(p1) > 0 else p2
sum = 0
for i in range(1, len(deck) + 1):
    sum += i * deck.pop()
print(sum)