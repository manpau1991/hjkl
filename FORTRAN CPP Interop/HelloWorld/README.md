General Information
-------------------

This program is a very small and simple example that demonstrates how to call a FORTRAN function 
from C.

How To Build
------------

Use your C and FORTRAN compiler of choice to build this program. We use `gcc` and `gfortran`.

Instructions for Linux:

1. Use
```shell
gfortran -c hello.f95
```
to compile the FORTRAN program without linking it yet. This will create a non-executable
`hello.o` object file.

2. In the same fashion, use
```shell
gcc -c main.c
```
to compile the C program without linking it. This step creates a non-executable `main.o`
object file.

3. Link the object files together using gfortran:
```shell
gfortran -o hw main.o hello.o
```
This creates an executable file called `hw`.

Done! You can now execute the program:
```shell
./hw
```
You should see the words `Hello, World from FORTRAN!` printed on your screen.
