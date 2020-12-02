import re
lines = [re.split(' |: |-', i) for i in open('input.txt')]
valid = 0
for l in lines:
    pos1, pos2 = [int(l[i]) - 1 for i in (0, 1)]
    rule, passwd = l[2], l[3]
    if (passwd[pos1] == rule) != (passwd[pos2] == rule):
        valid += 1
        
print(valid)