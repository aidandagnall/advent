def getCoordinates(str):
    str = ''.join('0' if i in 'FL' else '1' for i in str)
    return (int(str, 2))

passes = [i.strip() for i in open('input.txt', 'r')]
seatnumbers = [getCoordinates(i) for i in passes]

for i in range(1, max(seatnumbers)):
    if i not in seatnumbers:
        if i - 1 in seatnumbers and i + 1 in seatnumbers:
            print(i)