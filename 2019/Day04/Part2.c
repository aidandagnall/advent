#include <stdio.h>

int main(int argc, char **argv)
{
    int arr[6];
    int count = 0;
    
    for (int i = 124075; i < 580769; i++)
    {
        int increasing = 1;
        int containsPair = 0;
        int sameAsPrev[6] = {0, 0, 0, 0, 0, 0};
        int val = i;

        for (int j = 5; j >=0; j--)
        {
            arr[j] = val % 10;
            val = val / 10;
        }

        for(int j = 1; j < 6; j++)
        {
            if (arr[j] == arr[j - 1])
                sameAsPrev[j] = 1;

            if (arr[j] < arr[j-1])
                increasing = 0;
        }

        for (int j = 1; j < 5; j++)
            if (sameAsPrev[j] == 1)
                if (sameAsPrev[j - 1] == 0 && sameAsPrev[j + 1] == 0)
                    containsPair = 1;

        if (sameAsPrev[5] == 1 && sameAsPrev[4] == 0)
            containsPair = 1;
        
        if (increasing && containsPair)
            count++;
    }

    printf("%d\n", count);
    return 0;
}