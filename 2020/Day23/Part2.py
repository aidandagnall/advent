# Linked list node
class node:
    def __init__(self, l):
        self.label = l
        self.next = None

# performs one move in game with the current cup given
def play(curr):
    # create list of cups to remove
    removed = [locations[curr].next]
    for _ in range(2):
        removed.append(removed[-1].next)
    
    # fix link from before removed section to after moved section
    locations[curr].next = removed[-1].next
    
    # find destination node
    dest = locations[curr - 1]
    while dest in removed:
        dest = locations[dest.label - 1]
    if dest.label == 0:
        dest = locations[len(cups)]
        while dest in removed:
            dest = locations[dest.label - 1]
    
    # add links for moved section
    removed[-1].next = dest.next
    dest.next = removed[0]

    # return the next current cup
    curr = locations[curr].next.label
    return curr

input = [i.strip() for i in open('input.txt')][0]
cups = []
locations = {}

# add input
for i, c in enumerate(input):
    cups.append(int(c))
# add additional cups
for i in range(max(cups) + 1, 1000000 + 1):
    cups.append(i)

# create LL nodes for each cup and point them to their clockwise neighbour
for i in range(len(cups)):
    locations[cups[i]] = node(cups[i])
for i in range(len(cups) - 1):
    locations[cups[i]].next = locations[cups[i + 1]]
# join end to start to make it circular
locations[cups[-1]].next = locations[cups[0]]
# add blank node to avoid errors when decrementing current cup tracker
locations[0] = node(0)
curr = cups[0]
loops = 10000000

# make move number of times given by loops
for i in range(1, loops + 1):
    print(f'-- move {i} --')
    curr = play(curr)
print(f'next: {locations[1].next.label}')
print(f'next next: {locations[1].next.next.label}')
print(f'prod: {locations[1].next.label * locations[1].next.next.label}')