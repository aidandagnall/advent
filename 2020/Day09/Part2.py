input = [int(i) for i in open('input.txt', 'r')]
goal = 22406676

for i in range(0, len(input)):
    j = 0
    while sum(input[i:i+j]) < goal:
        j += 1
    if sum(input[i:i+j]) == goal:
        r = input[i:i+j]
        print(f'Goal found {min(r)} to {max(r)} = {min(r) + max(r)}')
        break