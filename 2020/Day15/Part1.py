input = [int(i) for i in open('input.txt').readline().strip().split(',')]

for i in range(2020):
    prev = input[-1]
    if prev in input[:-1]:
        occurences = [i for i in reversed(range(len(input))) if input[i] == prev]
        input.append(occurences[0] - occurences[1])
    else:
        input.append(0)
print(input[2020 - 1])