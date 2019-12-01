#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv)
{
    FILE *fp = fopen("mass.txt", "r");
    char string[100];
    int fuelTotal, fuelModule;

    while(fgets(string, sizeof(string), fp) != NULL)
    {
        sscanf(string, "%d", &fuelModule);
        fuelModule = fuelModule / 3 - 2;
        fuelTotal += fuelModule;
    }

    printf("%d\n", fuelTotal);
    return 0;
}