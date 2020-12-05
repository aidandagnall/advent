def getCoordinates(str):
    row, col = str[:7], str[7:]
    row = row.replace('F', '0').replace("B", "1")
    col = col.replace("L", "0").replace("R", "1")
    return (int(row, 2), int(col, 2))
    
passes = [i.strip() for i in open('input.txt', 'r')]
seatnumbers = [(getCoordinates(i)[0] * 8 + getCoordinates(i)[1]) for i in passes]
print(max(seatnumbers))