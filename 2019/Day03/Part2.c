#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int main(int argc, char **argv)
{
    FILE *fp = fopen("input.txt", "r");
    int wireA[154000][3], wireB[154000][3], i = 1;
    int collisions[1000][3];
    wireA[0][0] = wireB[0][0] = 0;
    wireA[0][1] = wireB[0][1] = 0;
    char str[2900], *token;

    fgets(str, 1800, fp);
    token = strtok(str, ",");
    while(token != NULL)
    {
        switch (token[0])
        {
        case 'U':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireA[i][0] = wireA[i - 1][0];
                wireA[i][1] = wireA[i - 1][1] + 1;
                wireA[i][2] = i;
                i++;
            }
            
            break;
        case 'D':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireA[i][0] = wireA[i - 1][0];
                wireA[i][1] = wireA[i - 1][1] - 1;
                wireA[i][2] = i;
                i++;
            }
            
            break;
        case 'L':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireA[i][1] = wireA[i - 1][1];
                wireA[i][0] = wireA[i - 1][0] - 1;
                wireA[i][2] = i;
                i++;
            }
            break;
        case 'R':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireA[i][1] = wireA[i - 1][1];
                wireA[i][0] = wireA[i - 1][0] + 1;
                wireA[i][2] = i;
                i++;
            }
            break;
            
        default:
            break;
        }

        token = strtok(NULL, ",");
    }
    i = 1;
    fgets(str, 1600, fp);
    token = strtok(str, ",");
    
    while(token != NULL)
    {
        switch (token[0])
        {
        case 'U':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireB[i][0] = wireB[i - 1][0];
                wireB[i][1] = wireB[i - 1][1] + 1;
                wireB[i][2] = i;
                i++;
            }
            
            break;
        case 'D':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireB[i][0] = wireB[i - 1][0];
                wireB[i][1] = wireB[i - 1][1] - 1;
                wireB[i][2] = i;
                i++;
            }
            
            break;
        case 'L':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireB[i][1] = wireB[i - 1][1];
                wireB[i][0] = wireB[i - 1][0] - 1;
                wireB[i][2] = i;
                i++;
            }
            break;
        case 'R':
            for(int j = 0; j < atoi(token + 1); j++)
            {
                wireB[i][1] = wireB[i - 1][1];
                wireB[i][0] = wireB[i - 1][0] + 1;
                wireB[i][2] = i;
                i++;
            }
            break;
            
        default:
            break;
        }

        token = strtok(NULL, ",");
    }

    int collCount = 0;
    for (int j = 1; j < 154000; j++)
    {
        for (int k = 1; k < 154000; k++)
        {
            if (wireA[j][0] == 0 && wireA[j][1] == 0)
            {
                continue;
            }
            if (wireA[j][1] == 0)
            {
                continue;
            }
            if (wireA[j][0] == wireB[k][0] && wireA[j][1] == wireB[k][1])
            {
                collisions[collCount][0] = wireA[j][0];
                collisions[collCount][1] = wireA[j][1];
                collisions[collCount][2] = wireA[j][2] + wireB[k][2];
                collCount++;
            }
        }
    }

    int steps = 9999999;
    for (int j = 0; j < collCount; j++)
    {
        if ((collisions[j][2] < steps))
        {
            steps = collisions[j][2];
        }
    }

    printf("%d\n", steps);
    return 0;
}