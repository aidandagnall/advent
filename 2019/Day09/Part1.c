#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void decodeInstruction(int a, int ins[5]);

int main(int argc, char **argv)
{
    FILE *fp = fopen("input.txt", "r");
    char fullInput[4000], *token;
    long int intcode[100000];
    int ins[5];
    int position = 0;
    long int par1, par2, par3;
    int opcode;
    int base = 0;

    fgets(fullInput, sizeof(fullInput), fp);
    token = strtok(fullInput, ",");
    while(token != NULL)
    {
        intcode[position] = atol(token);
        position++;
        token = strtok(NULL, ",");
    }

    position = 0;

    while (intcode[position] != 99)
    {
        decodeInstruction(intcode[position], ins);
        
        opcode = ins[3] * 10 + ins[4];

        par1 = ins[2] ?  intcode[position + 1] : intcode[intcode[position + 1]];
        if (ins[2] == 2)
            par1 = intcode[base + intcode[position + 1]];
        par2 = ins[1] ? intcode[position + 2] : intcode[intcode[position + 2]];
        if (ins[1] == 2)
            par2 = intcode[base + intcode[position + 2]];
        par3 = ins[0] ? position + 3 : intcode[position + 3];
        if (ins[0] == 2)
            par3 = base + intcode[position + 3];

        switch (opcode)
        {
        case 1:
            intcode[par3] = par1 + par2;
            position += 4;
            break;
        case 2:
            intcode[par3] = par1 * par2;
            position += 4;
            break;
        case 3:
            if (ins[2] == 2)
                par1 = base + intcode[position + 1];
            scanf("%ld", &intcode[par1]);
            position += 2;
            break;
        case 4:
            printf("%ld\n", par1);
            position += 2;
            break;
        
        case 5:
            if (par1 != 0)
            {
                position = par2;
                break;
            }
            position += 3;
            break;
        case 6:
            if (par1 == 0)
            {
                position = par2;
                break;
            }
            position += 3;
            break;
        case 7:
            if (par1 < par2)
                intcode[par3] = 1;
            else
                intcode[par3] = 0;
            position += 4;
            break;
        
        case 8:
            if (par1 == par2)
                intcode[par3] = 1;
            else
                intcode[par3] = 0;
            position += 4;
            break;
        case 9:
            base += par1;
            position += 2;
            break;

        default:
            printf("Something went wrong... \n");
            return 1;
            break;
        }
    }

    return 0;
}

void decodeInstruction(int a, int ins[5])
{
    char str[5];
    int j = 4;
    sprintf(str, "%d", a);

    for(int i = 0; i < 5; i++)
        ins[i] = 0;
    for(int i = strlen(str) - 1; i >= 0; i--)
        ins[j--] = str[i] - '0';
}