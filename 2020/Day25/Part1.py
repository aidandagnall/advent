input = [int(i) for i in open('input.txt')]
card_key, door_key = input[0], input[1]
door_loop = card_loop = 0
subject_num = 1
loop_size = 1
while card_loop == 0 or door_loop == 0:
    subject_num = (subject_num * 7) % 20201227
    if subject_num == card_key:
        card_loop = loop_size
    if subject_num == door_key:
        door_loop = loop_size
    loop_size += 1

key_1 = 1
for _ in range(door_loop):
    key_1 = (key_1 * card_key) % 20201227

key_2 = 1
for _ in range(card_loop):
    key_2 = (key_2 * door_key) % 20201227

# both keys should match match
print(key_1)
print(key_2)