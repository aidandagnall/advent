import re
lines = [re.split(' |: |-', i) for i in open('input.txt')]
valid = 0
for l in lines:
    min, max = [int(l[i]) for i in (0, 1)]
    rule, passwd = l[2], l[3]
    if passwd.count(rule) >= min and passwd.count(rule) <= max:
        valid += 1
        
print(valid)