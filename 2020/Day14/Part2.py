def edit_memory(mask, address, value):
    bin_addr = '{0:036b}'.format(address)
    general_addr = ''
    for i, c in enumerate(mask):
        if c == 'X' or c == '1':    general_addr += c
        else:                       general_addr += bin_addr[i]
    floatcount = general_addr.count('X')
    for i in range(2**floatcount):
        modified_addr = general_addr
        bin_string = ('{0:0' + str(floatcount) +'b}').format(i)
        for j in range(floatcount):
            modified_addr = modified_addr.replace('X', bin_string[j], 1)
        mem[modified_addr] = value
    
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