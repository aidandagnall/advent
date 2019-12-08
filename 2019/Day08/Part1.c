#include <stdio.h>

int main(int argc, char **argv)
{
    int zeroCount = 0, oneCount = 0, twoCount = 0, max[3] = {25 * 6, 0, 0};
    char str[25 * 6];
    FILE *fp = fopen("input.txt", "r");

    while (fgets(str, 25 * 6 + 1, fp) != NULL)
    {
        zeroCount = oneCount = twoCount = 0;
        for (int i = 0; i < 25 * 6; i++)
        {
            if (str[i] == '0')
                zeroCount++;
            if (str[i] == '1')
                oneCount++;
            if (str[i] == '2')
                twoCount++;
        }

        if (zeroCount < max[0])
        {
            max[0] = zeroCount;
            max[1] = oneCount;
            max[2] = twoCount;
        }
    }
    printf("%d\n", max[1] * max[2]);
    return 0;
}