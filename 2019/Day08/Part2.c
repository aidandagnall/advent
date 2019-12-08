#include <stdio.h>

int main(int argc, char **argv)
{
    char str[25 * 6];
    int layer[100][25][6], final[25][6];;
    FILE *fp = fopen("input.txt", "r");

    for (int i = 0; i < 100; i++)
    {
        fgets(str, 25 * 6 + 1, fp);
        for (int j = 0; j < 6; j++)
            for (int k = 0; k < 25; k++)
                layer[i][k][j] = str[(25 * j) + k] - '0';
    }

    for (int i = 0; i < 6; i++)
    {
        for (int j = 0; j < 25; j++)
        {
            for (int k = 0; k < 100; k++)
                if (layer[k][j][i] != 2)
                {
                    final[j][i] = layer[k][j][i];
                    break;
                }

            if (final[j][i] == 0)
                printf(" ");
            else
                printf("*");
        }
        printf("\n");
    }

    return 0;
}