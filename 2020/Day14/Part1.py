def edit_memory(mask, address, value):
    bin_val = '{0:036b}'.format(value)
    output = ''
    for i, c in enumerate(mask):
        if c == 'X':    output += bin_val[i]
        else:           output += c
    mem[address] = int(output,2)

masks = []
input = [i.strip() for i in open('input.txt', 'r')]
mask =  { 'mask': "", 'mem': [] }
for line in input:
    if 'mask' in line:
        if mask != { 'mask': "", 'mem': [] }: 
            masks.append(mask)
            mask =  { 'mask': "", 'mem': [] }
        mask['mask'] = line.split(' = ')[1]
    else:
        arr = line.split(' = ')
        mask['mem'].append((int(arr[0][4:-1]), int(arr[1])))
masks.append(mask)
        
mem = {}
for m in masks:
    for index, value in m['mem']:
        edit_memory(m['mask'], index, value)

print(sum(mem.values()))