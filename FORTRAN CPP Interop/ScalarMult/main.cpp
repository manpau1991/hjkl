#include <iostream>

#include "fmatrix2d.h"

int main(void) {
    const unsigned int ROWS = 5;
    const unsigned int COLS = 3;

    // Constructs a new FORTRAN 2D-matrix with respect to the number of ROWS and COLS.
    FMatrix2D m(ROWS, COLS);

    // Fills the array with numbers from 1 to ROWS * COLS.
    for (unsigned int r = 1; r <= ROWS; ++r) {
        for (unsigned int c = 1; c <= COLS; ++c) {
            m(r, c) = (r - 1) * COLS + c;
        }
    }

    // Prints the matrix.
    std::cout << "Before:\n" << m << '\n';

    // Scales the matrix by a factor of 2.
    m.scalar_mult(2);

    // Prints the result.
    std::cout << "After:\n" << m << std::endl;

    return 0;
}
