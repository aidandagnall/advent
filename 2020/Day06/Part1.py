input = [i.strip() for i in open('input.txt', 'r')]
input = [i.strip() for i in ' '.join(',' if not j else j for j in input).split(',') if i]

responses = []
for group in input:
    responses.append({i for i in group if i != ' '})
print(sum(len(i) for i in responses))