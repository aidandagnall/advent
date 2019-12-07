#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int compute_intcode(int [5][1000], int [5]);
void decodeInstruction(int, int [5]);
void getInput();

int main(int agrc, char **argv)
{
    int intcode[5][1000], max = 0, output = 0;
    getInput(intcode);
    for (int a = 5; a < 10; a++)
    {
        for (int b = 5; b < 10; b++)
        {
            if (a == b)
                continue;
            for (int c = 5; c < 10; c++)
            {
                if (a == c || b == c)
                    continue;
                for (int d = 5; d < 10; d++)
                {
                    if (a == d || b == d || c == d)
                        continue;
                    for (int e = 5; e < 10; e++)
                    {
                        if (a == e || b == e || c == e || d == e)
                            continue;
                        int phases[5] = {a, b, c, d, e};
                        output = compute_intcode(intcode, phases);
                        if (output > max)
                            max = output;
                        getInput(intcode);
                    }
                }
            }
        }
    }

    printf("%d\n", max);
    return 0;
}

int compute_intcode(int intcode[5][1000], int phase[5])
{
    int input = 0, ins[5], amp = 0, positions[5] = {0, 0, 0, 0, 0}, phaseIn[5] = {0, 0, 0, 0, 0};
    int opcode, par1, par2, par3;

    while(intcode[4][positions[4]] != 99)
    {
        decodeInstruction(intcode[amp][positions[amp]], ins);
        opcode = ins[3] * 10 + ins[4];
        par1 = ins[2] ?  intcode[amp][positions[amp] + 1] : intcode[amp][intcode[amp][positions[amp] + 1]];
        par2 = ins[1] ? intcode[amp][positions[amp] + 2] : intcode[amp][intcode[amp][positions[amp] + 2]];
        par3 = ins[0] ? positions[amp] + 3 : intcode[amp][positions[amp] + 3];

        switch (opcode)
        {
        case 1:
            intcode[amp][par3] = par1 + par2;
            positions[amp] += 4;
            break;
        case 2:
            intcode[amp][par3] = par1 * par2;
            positions[amp] += 4;
            break;
        case 3:
            par1 = intcode[amp][positions[amp] + 1];
            if (phaseIn[amp] == 0)
                intcode[amp][par1] = phase[amp];
            else
                intcode[amp][par1] = input;
            positions[amp] += 2;
            phaseIn[amp] = 1;
            break;
        case 4:
            input = par1;
            positions[amp] += 2;
            amp++;
            if (amp == 5)
                amp = 0;
            break;
        case 5:
            if (par1 != 0)
            {
                positions[amp] = par2;
                break;
            }
            positions[amp] += 3;
            break;
        case 6:
            if (par1 == 0)
            {
                positions[amp] = par2;
                break;
            }
            positions[amp] += 3;
            break;
        case 7:
            if (par1 < par2)
                intcode[amp][par3] = 1;
            else
                intcode[amp][par3] = 0;
            positions[amp] += 4;
            break;
        case 8:
            if (par1 == par2)
                intcode[amp][par3] = 1;
            else
                intcode[amp][par3] = 0;
            positions[amp] += 4;
            break;
        case 99:
            if (amp == 4)
            {
                return input;
            }
            amp++;
            break;
        default:
            printf("Something went wrong... \n");
            return 1;
            break;
        }
    }

    return input;
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

void getInput(int intcode[5][1000])
{
    char fullInput[3000], *token;
    int position = 0;

    FILE *fp = fopen("input.txt", "r");
    fgets(fullInput, sizeof(fullInput), fp);
    token = strtok(fullInput, ",");
    while(token != NULL)
    {
        for (int i = 0; i < 5; i++)
            intcode[i][position] = atoi(token);
        position++;
        token = strtok(NULL, ",");
    }
    fclose(fp);
}