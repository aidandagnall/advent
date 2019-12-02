#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int processIntcode(int [250]);
void copyArray(int [250], int dst[250]);

int main(int argc, char **argv)
{
    FILE *fp = fopen("input.txt", "r");
    char fullInput[500], *token;
    int intcode[250], copy[250];
    int position;

    fgets(fullInput, sizeof(fullInput), fp);
    token = strtok(fullInput, ",");
    while(token != NULL)
    {
        intcode[position] = atoi(token);
        position++;
        token = strtok(NULL, ",");
    }
    
    for (int noun = 0; noun <= 99; noun++)
    {
        for (int verb = 0; verb <= 99; verb++)
        {
            copyArray(intcode, copy);
            copy[1] = noun;
            copy[2] = verb;

            if (processIntcode(copy) == 19690720)
            {
                printf("noun = %d, verb = %d\n", noun, verb);
                return 0;
            }
        }
    }

    return 1;
}

void copyArray(int src[250], int dest[250])
{
    for (int i = 0; i < 250; i++)
    {
        dest[i] = src[i];
    }
}

int processIntcode(int intcode[250])
{
    int position = 0;

    while (intcode[position] != 99)
    {
        switch (intcode[position])
        {
        case 1:
            intcode[intcode[position + 3]] = intcode[intcode[position + 1]] + intcode[intcode[position + 2]];
            break;
        case 2:
            intcode[intcode[position + 3]] = intcode[intcode[position + 1]] * intcode[intcode[position + 2]];
            break;
        
        default:
            printf("Something went wrong... \n");
            return 0;
            break;
        }
        position += 4;
    }

    return intcode[0];

}