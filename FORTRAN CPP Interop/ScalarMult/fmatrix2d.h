#ifndef FMATRIX2D_H
#define FMATRIX2D_H

#include <iostream>

/** Represents a two-dimensional FORTRAN matrix. */
class FMatrix2D {
    private:
        /** The number of rows in the matrix. */
        const int ROWS;

        /** The number of columns in the matrix. */
        const int COLS;

        /** The data of the matrix stored in column-major order. */
        double *data;

    public:
        /**
         * Constructs a new FORTRAN matrix with respect to the given number of rows and columns.
         *
         * @param rows the number of rows
         * @param cols the number of columns
         */
        FMatrix2D(const int rows, const int cols):
            ROWS(rows), COLS(cols), data(new double[rows * cols]) {  }

        /** Destroys this matrix. */
        ~FMatrix2D() {
            delete[] data; // Releases memory.
        }

        /** Returns the data of the current matrix linearized in column-major order. */
        double* get_data() const {
            return data;
        }

        /**
         * Multiplies this matrix by the given scalar value.
         *
         * @param scalar the value to scale by
         */
        void scalar_mult(const double scalar);

        /**
         * Accesses the data of the current matrix at row `r` and column `c` (FORTRAN-style
         * syntax). Please note that, as a contrast to C, indizes start at 1 in FORTRAN.
         *
         * @param r the row of the matrix to access
         * @param c the column of the matrix to access
         * @return the data stored at row r and column c
         */
        double& operator()(const unsigned int r, const unsigned int c);

        /** Pretty prints the current matrix. */
        friend std::ostream& operator<<(std::ostream &out, FMatrix2D &matrix);
};

#endif
