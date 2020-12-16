import re

# Read input
with open('input.txt', 'r') as f:
    rules = []
    for line in f:
        if line.strip() == '': break
        matches = [int(i) for i in re.findall('[0-9]+', line.strip())]
        rulerange = list(range(matches[0], matches[1] + 1)) + list(range(matches[2], matches[3] + 1))
        rules.append(rulerange)
    f.readline()
    myticket = [int(x) for x in re.findall('[0-9]+', f.readline().strip())]
    f.readline()
    f.readline()
    tickets = [[int(x) for x in re.findall('[0-9]+', line.strip())] for line in f]

# print the sum of the values that are do not fit any rules
print(sum( [ v if not any(v in r for r in rules) else 0 for t in tickets for v in t ] ))