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

# create set of all possible ingredients for each allergen
possible_ingredients = {}
for allergen in allergens:
    maxcount = max(Counter(allergens[allergen]).values())
    for a in allergens[allergen]:
        if allergens[allergen].count(a) == maxcount:
            if allergen not in possible_ingredients:
                possible_ingredients[allergen] = set()
            possible_ingredients[allergen].add(a)

# deduce each ingredient for each allergen
found = []
while len(found) < len(allergens):
    for a in possible_ingredients:
        if len(possible_ingredients[a]) == 1:
            found.append( (a, list(possible_ingredients[a])[0]) )
            break
    possible_ingredients.pop(found[-1][0])
    for a in possible_ingredients:
        if found[-1][1] in possible_ingredients[a]:
            possible_ingredients[a].remove(found[-1][1])

# generate string
print(','.join([i for a,i in sorted(found, key=lambda x: x[0])]))
