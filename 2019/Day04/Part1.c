#include <stdio.h>

int main(int argc, char **argv)
{
    int arr[6];
    int count = 0;
    
    for (int i = 124075; i < 580769; i++)
    {
        int increasing = 1;
        int adjacent = 0;
        int val = i;

        for (int j = 5; j >=0; j--)
        {
            arr[j] = val % 10;
            val = val / 10;
        }

        for(int j = 1; j < 6; j++)
        {
            if (arr[j] == arr[j - 1])
                adjacent++;

            if (arr[j] < arr[j-1])
                increasing = 0;
        }
        
        if (increasing && adjacent)
            count++;
    }

    printf("%d\n", count);
    return 0;
}