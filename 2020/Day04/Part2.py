import re
def isValid(p):
    fields = 0
    for k in p:
        if k == 'byr':
            if int(p['byr']) < 1920 or int(p['byr']) > 2002:
                return False
            fields += 1
        if k == 'iyr':
            if int(p['iyr']) < 2010 or int(p['iyr']) > 2020:
                return False
            fields += 1

        if k == 'eyr':
            if int(p['eyr']) < 2020 or int(p['eyr']) > 2030:
                return False
            fields += 1
        if k == 'hgt':
            if 'cm' in p['hgt']:
                height = int(p['hgt'][0:-2])
                if height < 150 or height > 193:
                    return False
            elif 'in' in p['hgt']:
                height = int(p['hgt'][0:-2])
                if height < 59 or height > 76:
                    return False
            else:
                return False
            fields += 1
        if k == 'hcl':
            if not re.search(r'^#(?:[0-9a-fA-F]{3}){1,2}$', p['hcl']):
                return False
            fields += 1
        if k == 'ecl':
            colours = ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']
            if p['ecl'] not in colours:
                return False
            fields += 1
        if k == 'pid':
            if len(p['pid']) != 9:
                return False
            fields += 1
    if fields < 7:
        return False
    return True
            

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

fields = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']
valid = 0
for p in passports:
    if isValid(p):
        valid += 1

print(valid)