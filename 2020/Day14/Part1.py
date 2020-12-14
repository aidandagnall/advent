def edit_memory(mask, address, value):
    bin_val = '{0:036b}'.format(value)
    output = "".join([c if c !='X' else bin_val[i] for i,c in enumerate(mask)])
    mem[address] = int(output,2)

input = [i.strip() for i in open('input.txt', 'r')]
mem = {}
mask = ''
for line in input:
    if 'mask' in line:
        mask = line.split(' = ')[1]
    else:
        arr = line.split(' = ')
        edit_memory(mask, int(arr[0][4:-1]), int(arr[1]))

print(sum(mem.values()))