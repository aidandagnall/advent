from collections import Counter
input = [i.strip() for i in open('input.txt')]

# parse input
menu = []
allergens = {}
ingredients = set()
for line in input:
    new_line = line.replace('(', '').replace(')', '').replace(',', '')
    new_line = new_line.split(' ')
    menu_item = {'i': [], 'a':[]}
    for word in new_line[:new_line.index('contains')]:
        menu_item['i'].append(word)
        ingredients.add(word)
    for word in new_line[new_line.index('contains') + 1:]:
        menu_item['a'].append(word)
        if word not in allergens:
            allergens[word] = []
        for ingredient in menu_item['i']:
            allergens[word].append(ingredient)
    menu.append(menu_item)

# remove all elements that do not appear consistently for all allergens
contains_allergens = set()
for allergen in allergens:
    maxcount = max(Counter(allergens[allergen]).values())
    for a in allergens[allergen]:
        if allergens[allergen].count(a) == maxcount:
            contains_allergens.add(a)

# get all ingredients that do not contain allergens
no_allergens = ingredients.difference(contains_allergens)

# get number of appearances for all non-allergen ingredients
print(sum([len([j for j in menu if i in j['i']]) for i in no_allergens]))