/* Simple pointer allocation using call-by-reference. (c) Thomas Lang, 2016. -*- c -*- */

#include <stdio.h>
#include <stdlib.h>

#define SIZE 100

/* I personally prefer this one. */
static void alloc_by_ref(int** p)
{
    *p = (int*) malloc(SIZE * sizeof(int));

    if (!*p) {
        fprintf(stderr, "Could not allocate 100 int's, what a shitty machine ...\n");
        exit(EXIT_FAILURE);
    }
}

/* (puke) */
static int* alloc_by_ret(void)
{
    int *p = (int*) malloc(SIZE * sizeof(int));

    if (!p) {
        fprintf(stderr, "Could not allocate 100 int's, what a shitty machine ...\n");
        exit(EXIT_FAILURE);
    }

    return p;
}

void generate_values(int* p)
{
    for (unsigned i = 0; i < SIZE; ++i)
        p[i] = 42 * i - 7;
}

uint are_equal(int* p1, int* p2)
{
    for (unsigned i = 0; i < SIZE; ++i)
        if (p1[i] != p2[i])
            return 0;
    return 1;
}

int main(int argc, char* argv[])
{
    int *p1, *p2;

    printf("Allocating per call-by-reference ... ");
    alloc_by_ref(&p1);
    printf("done.\n");

    printf("Allocating per returning of the pointer ... ");
    p2 = alloc_by_ret();
    printf("done.\n");

    printf("Filling 'em with values ... ");
    generate_values(p1);
    generate_values(p2);
    printf("done.\n");

    printf("They are both");
    
    if (!are_equal(p1, p2))
        printf(" not");

    printf(" equal.\n");
    return 0;
}
