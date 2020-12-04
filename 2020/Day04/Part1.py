input = [i.strip() for i in open('input.txt', 'r')]
input = [i.strip() for i in ' '.join(',' if not j else j for j in input).split(',') if i]

passports = []
for entry in input:
    passport = {}
    for field in entry.split(" "):
        passport[field.split(':')[0]] = field.split(':')[1]
    passports.append(passport)

keys = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']
validpassports = [p for p in passports if set(keys).issubset(set(p.keys()))]
print(len(validpassports))