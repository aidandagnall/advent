def getCoordinates(str):
    str = ''.join('0' if i in 'FL' else '1' for i in str)
    return (int(str, 2))
    
passes = [i.strip() for i in open('input.txt', 'r')]
seatnumbers = [getCoordinates(i) for i in passes]
print(max(seatnumbers))