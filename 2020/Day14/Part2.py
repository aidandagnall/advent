def edit_memory(mask, address, value):
    bin_addr = '{0:036b}'.format(address)
    addr = ''.join([bin_addr[i] if c =='0' else c for i,c in enumerate(mask)])
    floatcount = addr.count('X')
    for i in range(2**floatcount):
        modified_addr = addr
        bin_string = ('{0:0' + str(floatcount) +'b}').format(i)
        for j in range(floatcount):
            modified_addr = modified_addr.replace('X', bin_string[j], 1)
        mem[modified_addr] = value
    
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