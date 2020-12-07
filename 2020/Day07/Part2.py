import re
def contains(str):
    bags = 1
    for i in [bag for bag in baginfo if bag[0] == str]:
        for j in i[1:]:
            if j == 'no other':
                return 1
            n = int(j.strip().split(' ')[0])
            bag = ' '.join(j.strip().split(' ')[1:])
            bags += n * contains(bag)
    return bags

baginfo = []
for i in open('input.txt', 'r'):
    l = list(filter(None, re.split(' bags contain |bags, |, | bag.', i.strip())[:-1]))
    baginfo.append([i.strip() for i in l])

print(contains('shiny gold') - 1)