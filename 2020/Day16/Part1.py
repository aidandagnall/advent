import re
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
    tickets = [re.findall('[0-9]+', line.strip()) for line in f]

invalid = 0
for t in tickets:
    for v in t:
        if not any(int(v) in rule for rule in rules):
            invalid += int(v)
            break
print(f'invalid: {invalid}  total: {len(tickets)}')
