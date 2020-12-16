import re

def is_ticket_valid(t):
    for v in t:
        if not any(int(v) in rule for rule in rules):
            return False
    return True

with open('input.txt', 'r') as f:
    rules = []
    for line in f:
        if line.strip() == '': break
        matches = [int(i) for i in re.findall('[0-9]+', line.strip())]
        rulerange = list(range(matches[0], matches[1] + 1)) + list(range(matches[2], matches[3] + 1))
        rules.append(rulerange)
    f.readline()
    myticket = re.findall('[0-9]+', f.readline().strip())
    f.readline()
    f.readline()
    tickets = list(filter(is_ticket_valid, [re.findall('[0-9]+', line.strip()) for line in f]))

valid_cols = {}

for j, r in enumerate(rules):
    valid_cols[j] = []
    for i in range(3, len(myticket)):
        if all(int(t[i]) in r for t in tickets):
            valid_cols[j].append(i)
    print(f'j: {j} len: {len(valid_cols[j])} :  {valid_cols[j]}')

# Worked this out by hand...

valid_departure_cols = [14, 3, 15, 17, 4, 12]
prod = 1
for v in valid_departure_cols:
    prod *= int(myticket[v])
print(prod)