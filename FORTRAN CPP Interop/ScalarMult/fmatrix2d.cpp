#include "fmatrix2d.h"

#include <stdexcept>

extern "C" {
    // This is the FORTRAN function we want to call.
    void scalar_mult_(double *matrix, const int *rows, const int *cols, const double *scalar);
}

void FMatrix2D::scalar_mult(const double scalar) {
    // Delegates to the external FORTRAN function declared above. This is a trick to satisfy
    // the linker.
    scalar_mult_(get_data(), &ROWS, &COLS, &scalar);
}

double& FMatrix2D::operator()(const unsigned int row, const unsigned int column) {

    // Makes sure we're not out of boundaries.
    if (row < 1 || ROWS < row || column < 1 || COLS < column) {
        throw std::out_of_range("Index out of bounds");
    }

    /*
     * Now comes the tricky part: We have to convert the column-major logic used in FORTRAN
     * to row-major logic used in C. Moreover, we have to keep in mind that array indices start
     * with 0 in C.
     *
     * Example: Assume we have the following matrix:
     *
     *        1 2
     *   M =  3 4
     *        5 6
     *
     * In FORTRAN, the values of M are stored by columns, i.e., in memory, M is represented by
     * this block:
     *
     *   [1 3 5 | 2 4 6]
     *
     * First of all, if we want to access the 4, which is the element in the 2nd row and 2nd
     * column, we have to write M(2,2). But in C, indices start at 0. Thus, we have to decrement
     * row and column and write M[1][1].
     * Second, since we stored the data in column-major order, the column multiplied by the number
     * of entries in a column (which is exactly the number of rows) tells us, how far we have to
     * "walk" to get into the sub-block where our desired number is. The offset within this
     * particular block is then given by the row.
     * To conclude, the formula for accessing the elements is:
     *
     *      Matrix(row, column) = column * #rows + row
     */
    return data[(column - 1) * ROWS + (row - 1)];
}

std::ostream& operator<<(std::ostream &out, FMatrix2D &matrix) {
    for (unsigned int i = 1; i <= matrix.ROWS; ++i) {
        std::cout << "| ";
        for (unsigned int j = 1; j <= matrix.COLS; ++j) {
            std::cout << matrix(i,j) << " ";
        }
        std::cout << "|\n";
    }
    return out;
}
