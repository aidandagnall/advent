import re
from math import prod

# Used to filter invalid tickets from Part 1
def is_ticket_valid(t):
    for v in t:
        if not any(v in rule for rule in rules):
            return False
    return True

# Read input
with open('input.txt', 'r') as f:
    rules = []
    for line in f:
        if line.strip() == '': break
        m = [int(i) for i in re.findall('[0-9]+', line.strip())]
        rules.append(list(range(m[0], m[1] + 1)) + list(range(m[2], m[3] + 1)))
    f.readline()
    myticket = [int(x) for x in re.findall('[0-9]+', f.readline().strip())]
    f.readline()
    f.readline()
    tickets = list(filter(is_ticket_valid , [[int(x) for x in re.findall('[0-9]+', line.strip())] for line in f]))

# For each rule, find all columns that comply
valid_cols = []
for j, r in enumerate(rules):
    valid_cols.append([])
    for i in range(0, len(myticket)):
        if all(t[i] in r for t in tickets):
            valid_cols[j].append(i)

# Working from the rule with the fewest (1) valid columns, work backwards to
# determine which column applies to which rule
found = []
for i, c in enumerate(sorted(valid_cols, key=len)):
    orig_index = [i for i,d in enumerate(valid_cols) if c == d ][0]
    valid_cols[orig_index] = list(filter(lambda v: v not in found, c))[0]
    found = c

# Print the product of the values from own ticket in the columns found to be
# for the departure info
print(prod([ myticket[d] for d in valid_cols[0:6] ]))