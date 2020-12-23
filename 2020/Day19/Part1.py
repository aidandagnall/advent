import re

def get_rule(a):
    r = rules[a]
    if r in ['a', 'b']:
        return r
    return '(' + ''.join('|' if c == '|' else get_rule(c) for c in r) + ')'

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

rule = get_rule(0) + '\Z'
print(len([i for i in messages if re.match(rule, i)]))