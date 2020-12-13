f = open('input.txt')
arrivaltime = int(f.readline().strip())
buses = [int(i) for i in f.readline().strip().split(',') if i != 'x']

bestbus, besttime = 0, 2*arrivaltime
for bus in buses:
    soonest = arrivaltime + (bus - (arrivaltime % bus))
    if besttime > soonest: bestbus, besttime = bus, soonest

print(f'Bus ID: {bestbus}  Earliest Time: {besttime}   Product: {bestbus * (besttime-arrivaltime)}')