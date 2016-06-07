General Information
-------------------
This time we call a FORTRAN function that performs a small computation and reuse that result in
our C program.

Notes
-----
1. Use the C data types that correspond to the FORTRAN data types. For example:

   C             | FORTRAN
   --------------|----------
   `int`         | `INTEGER`
   `float`       | `REAL`
   `double`      | `REAL*8`
   `long double` | `REAL*16`

2. In FORTRAN, variables are always passed by reference. This needs to be respected in the C
   program.

How To Build
------------

1. The first step is to compile the FORTRAN source without linking it:

   ```shell
   gfortran -c fpow.f95
   ```

   This creates a non-executable object file called `fpow.o`.
2. Second, you need to compile the C source without linking it:

   ```shell
   gcc -c main.c
   ```

   This also creates a non-executable object file, this time called `main.o`.
3. Last but not least, we need to link the object files together to create an executable file.
   We use gfortran for this:

   ```shell
   gfortran -o pow fpow.o main.o
   ```

   This will create an executable file called `pow`.

To execute the program, simply enter the command
```shell
./pow
```
You should see `2.000000^8.000000 = 256.000000` printed on your screen.
