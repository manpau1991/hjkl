/*
 * This is the function prototype of the FORTRAN function we want to call. Of course, the
 * FORTRAN function will not be known to the C compiler. Thus, we have to postulate the existence
 * of the function first.
 * The convention is to use the name of the FORTRAN function written all lower-case, followed
 * by an underscore at the end.
 */
void hello_();

int main(int argc, char* argv[])
{
    hello_(); // Calling the FORTRAN function.
    return 0;
}
