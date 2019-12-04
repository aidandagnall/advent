#include <stdio.h>

int main(int argc, char **argv)
{
    int arr[6];
    int val, adj, count = 0;
    int invalid;
    for (int i = 124075; i < 580769; i++)
    {
        val = i;
        adj = 0;
        invalid = 0;
        for (int j = 5; j >=0; j--)
        {
            arr[j] = val % 10;
            val = val / 10;
        }

        
        for(int j = 1; j < 6; j++)
        {
            if (arr[j] == arr[j-1])
            {
                adj++;
            }

            if (arr[j] < arr[j-1])
            {
                invalid = 1;
                continue;
            }
        }

        if (adj == 0 || invalid == 1)
        {
            continue;
        }

        count++;
    }

    printf("%d\n", count);
    return 0;
}