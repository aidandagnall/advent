#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char **argv)
{
    FILE *fp = fopen("input.txt", "r");
    char fullInput[500], *token;
    int intcode[250];
    int position;

    fgets(fullInput, sizeof(fullInput), fp);
    token = strtok(fullInput, ",");
    while(token != NULL)
    {
        intcode[position] = atoi(token);
        position++;
        token = strtok(NULL, ",");
    }

    position = 0;

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
            return 1;
            break;
        }
        position += 4;
    }

    printf("The value at position 0 is: %d\n", intcode[0]);

    return 0;
}