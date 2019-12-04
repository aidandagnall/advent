#include <stdio.h>

int main(int argc, char **argv)
{
    int arr[6];
    int val, count = 0;
    int invalid, containsPair = 0;
    int sameAsPrev[6];
    for (int i = 124075; i < 580769; i++)
    {
        val = i;
        invalid = 0;
        containsPair = 0;
        for(int j = 0; j < 6; j++)
        {
            sameAsPrev[j] = 0;
        }

        for (int j = 5; j >=0; j--)
        {
            arr[j] = val % 10;
            val = val / 10;
        }

        
        for(int j = 1; j < 6; j++)
        {
            if (arr[j] == arr[j - 1])
            {
                sameAsPrev[j] = 1;
            }

            if (arr[j] < arr[j-1])
            {
                invalid = 1;
                continue;
            }
        }

        for (int j = 1; j < 5; j++)
        {
            if (sameAsPrev[j] == 1)
            {
                if (sameAsPrev[j - 1] == 0 && sameAsPrev[j + 1] == 0)
                {
                    containsPair = 1;
                    break;
                }
            }
        }

        if (sameAsPrev[5] == 1 && sameAsPrev[4] == 0)
        {
            containsPair = 1;
        }
        

        if (invalid == 1 || containsPair == 0)
        {
            continue;
        }

        count++;
    }

    printf("%d\n", count);
    return 0;
}