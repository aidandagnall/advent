input = [0] + sorted([int(i) for i in open('input.txt', 'r')])
input.append(max(input) + 3)
diff = [0,0,0,0]
for i in range(0, len(input) - 1):
    diff[input[i + 1] - input[i]] += 1
    
print(f'd1:{diff[1]} * d3:{diff[3]} = {diff[1]*diff[3]}')