import re
def isValid(p):
    keys = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']
    if not set(keys).issubset(set(p.keys())):
        return False

    if not int(p['byr']) in range(1920, 2003):
        return False
    if not int(p['iyr']) in range(2010, 2021):
        return False
    if not int(p['eyr']) in range(2020, 2031):
        return False 
    if not hgt(p['hgt']):
        return False   
    if not re.search(r'^#(?:[0-9a-fA-F]{3}){1,2}$', p['hcl']):
        return False
    if p['ecl'] not in ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']:
        return False  
    if len(p['pid']) != 9:
        return False
    return True

def hgt(h):
    if 'cm' in h:
        return int(h[:-2]) in range(150, 194)
    elif 'in' in h:
        return int(h[:-2]) in range(59, 77)

rawinput = [i.strip() for i in open('input.txt', 'r')]
input = [i.strip() for i in ' '.join(',' if not j else j for j in rawinput).split(',') if i]

passports = []
for entry in input:
    passport = {}
    for field in entry.split(" "):
        passport[field.split(':')[0]] = field.split(':')[1]
    passports.append(passport)

validpassports = [p for p in passports if isValid(p)]
print(len(validpassports))