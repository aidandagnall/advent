def swap(i):
    if input[i][0] == 'jmp':
        input[i][0] = 'nop'
    elif input[i][0] == 'nop':
        input[i][0] = 'jmp'

def run():
    j = acc = 0
    executed = []
    while 0 <= j < len(input) and j not in executed:
        executed.append(j)
        if input[j][0] == 'acc':
            acc += int(input[j][1])
        elif input[j][0] == 'jmp':
            j += int(input[j][1])
            continue
        j += 1

    if (j == len(input)):
        return acc

input = [i.split() for i in open('input.txt', 'r')]
for i in range(0, len(input)):
    swap(i)
    out = run()
    if out != None:
        print(out)
        break
    swap(i)