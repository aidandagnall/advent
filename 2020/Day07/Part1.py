import re
baginfo = []
for i in open('input.txt', 'r'):
    l = list(filter(None, re.split(' bags contain |bags, |, | bag.', i.strip())[:-1]))
    baginfo.append([i.strip() for i in l])

allowedbags = ['shiny gold']
for validbag in allowedbags:
    for bag in baginfo:
        if any(validbag in k for k in bag[1:]):
            allowedbags.append(bag[0])

print(len(set(allowedbags)) - 1)