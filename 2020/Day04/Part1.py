f = open('input.txt', 'r')
passports = []
passport = {}
for line in f:
    if not line.strip():
        passports.append(passport)
        passport = {}
        continue
    k = line.strip().split(" ")
    for j in k:
        l = j.split(":")
        passport[l[0]] = l[1]
if passport != {}:
    passports.append(passport)
valid = 0
for p in passports:
    if len(p) == 8:
        valid += 1
    elif len(p) == 7 and not 'cid' in p:
        valid += 1

print(valid)