#include <stdio.h>

/*
 * This is the FORTRAN function we want to call in our code.
 * Note that FORTRAN always passes variables by reference. Also make sure to use the correct data
 * types here. E.g., REAL*8 in FORTRAN corresponds to double in C.
 * Another important point is that the return value is passed by value and not by reference.
 * As usual, the convention is to use the name of the FORTRAN function written in lower-case,
 * followed by an underscore.
 */
double fpow_(double *xbase, double *xexponent);

int main(int argc, char* argv[]) {
    double base = 2.0;
    double exponent = 8.0;

    // We need to pass a reference to those variables (see the comment above).
    double result = fpow_(&base, &exponent);

    // Pretty prints the calculation and its result.
    printf("%lf^%lf = %lf\n", base, exponent, result);

    return 0;
}
