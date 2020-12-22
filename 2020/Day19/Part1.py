import re
import itertools

def get_rule(a):
    if rules[a] in ['a', 'b']:
        return rules[a]
    L = []
    r = rules[a]
    if '|' in r:
        l = get_rule(r[0])
        for i in r[1 : r.index('|')]:
            l = list(itertools.product(l, get_rule(i)))
        m = get_rule(r[r.index('|') + 1])
        for i in r[r.index('|') + 2 : ]:
            m = list(itertools.product(m, get_rule(i)))
        for i in l + m:
            L.append(''.join(i))
        return L
    l = get_rule(r[0])
    for i in r[1:]:
        l = list(itertools.product(l, get_rule(i)))
    for i in l:
        L.append(''.join(i))
    return L

rules = {}
messages = []
with open('input.txt') as f:
    for line in f:
        if line.strip() == '': break
        line = line.split(': ')
        links = [int(i) if i != '|' else i for i in re.findall('[0-9]+|\|', line[1].strip())]
        rules[int(line[0])] = links
        if "a" in line[1]: rules[int(line[0])] = "a"
        if "b" in line[1]: rules[int(line[0])] = "b"
    messages = [i.strip() for i in f]

rule = get_rule(0)
print(len([i for i in messages if i in rule]))