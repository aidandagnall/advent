input = [int(i) for i in open('input.txt').readline().strip().split(',')]

nums = {}
for i, j in enumerate(input):
    nums[j] = (0, i + 1)

prev = input[-1]
for i in range(len(input) + 1, 30000000 + 1):
    if prev in nums:
        if nums[prev][0] == 0:  prev = 0
        else:                   prev = nums[prev][1] - nums[prev][0]
        
        if prev in nums:    nums[prev] = (nums[prev][1], i)
        else:               nums[prev] = (0, i)
    else:
        prev = 0
        nums[0] = (nums[0][1], i)
print(prev)