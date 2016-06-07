General Information
-------------------
FORTRAN features powerful built-in matrix operations. The intention is to leverage this
functionality in our C++ programs. We demonstrate how this goal can be achieved using the
simplified example of multiplying a scalar with a matrix. The key idea here is to write a small
C++ class that represents a FORTRAN matrix in linearized column-major order.

Notes
-----
1. In FORTRAN, matrices are stored in COLUMN-major order. In C, matrices are stored in ROW-major
   order. For example, consider the following matrix:

   ```
       | 1 2 3 |           FORTRAN: M(3,3)
   M = | 4 5 6 |                 C: M[3][3]
       | 7 8 9 |
   ```

      - In FORTRAN, `M` is stored along the columns: `[1 4 7 | 2 3 8 | 3 6 9]`
      - In C, `M` is stored along the rows: `[1 2 3 | 4 5 6 | 7 8 9]`

2. In FORTRAN, array indices start with 1. In C, array indices start with 0.

3. In C, a two-dimensional array is often expressed as an array of pointers, e.g. `double **x`,
   which FORTRAN does not recognize. To solve this problem, we have to use a one-dimensional array
   and store the values in column-major order as explained above.

How To Build
------------
This program comes with a `Makefile`. Simply enter
```shell
make
```
to build the program. This creates an executable called `fmat`, which can be run using the command
```shell
./fmat
```
The program scales a given matrix by a factor of 2. You should see the following printed on your
screen:
```shell
Before:
| 1 2 3 |
| 4 5 6 |
| 7 8 9 |
| 10 11 12 |
| 13 14 15 |

After:
| 2 4 6 |
| 8 10 12 |
| 14 16 18 |
| 20 22 24 |
| 26 28 30 |
```
To remove the executable and all object files enter the command
```shell
make clean
```
