input = [i.split() for i in open('input.txt', 'r')]
executed = []
i = acc = 0
while i not in executed:
    executed.append(i)
    if input[i][0] == 'acc':
        acc += int(input[i][1])
    elif input[i][0] == 'jmp':
        i += int(input[i][1])
        continue
    i += 1
print(acc)