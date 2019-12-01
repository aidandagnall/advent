#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
int main(int argc, char **argv)
{
    FILE *fp = fopen("mass.txt", "r");
    char string[100];
    int fuelTotal, fuelModule, fuelExtra;

    while(fgets(string, sizeof(string), fp) != NULL)
    {
        sscanf(string, "%d", &fuelModule);
        fuelModule = fuelModule / 3 - 2;

        fuelExtra = fuelModule;
        while (fuelExtra > 0)
        {
            fuelExtra = fuelExtra / 3 - 2;
            if (fuelExtra > 0)
            {
                fuelModule += fuelExtra;
            }
        }
        fuelTotal += fuelModule;
    }

    printf("%d\n", fuelTotal);
    return 0;
}