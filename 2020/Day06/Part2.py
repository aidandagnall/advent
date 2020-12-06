input = [i.strip() for i in open('input.txt', 'r')]
input = [i.strip() for i in ' '.join(',' if not j else j for j in input).split(',') if i]

responses = []
for group in input:
    groupsize = len(group.split(' '))
    responses.append({i for i in group.split(' ')[0] if group.count(i) == groupsize})
print(sum(len(i) for i in responses))