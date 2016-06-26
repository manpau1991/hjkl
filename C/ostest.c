/* Does some insane preprocessor checking. (c) Thomas Lang, 2016.  -*- c -*- */

#include <stdio.h>

/* Very rough platform check */
#define AMD64    (defined(__amd64__)  || defined(__amd64))
#define X86_64   (defined(__x86_64__) || defined(__x86_64))
#define ON_INTEL (AMD64 || X86_64)

/* Not detailed compiler check, some checks may be missing, though. */
/* Check for icc and clang first, as these two also define the __GNUC__ pragma. */
#if (defined(__INTEL_COMPILER) || defined(__ICC))
# ifdef __cplusplus
#   define COMPILER "ICPC"
# else
#   define COMPILER "ICC"
# endif
# define COMPILER_VERSION (__GNUC__ * 10000 + __GNUC_MINOR__ * 100 + __GNUC_PATCHLEVEL__)
#elif defined(__clang__) || defined(__clang)
# define CLANG
# define COMPILER "CLANG"
# define COMPILER_VERSION __clang_version__
#elif defined(__GNUC__)
# ifdef __cplusplus
#   define COMPILER "G++"
# else
#   define COMPILER "GCC"
# endif
# define COMPILER_VERSION (__GNUC__ * 10000 + __GNUC_MINOR__ * 100 + __GNUC_PATCHLEVEL__)
#endif

int main(int argc, char* argv[])
{
    printf("This program runs");

#ifndef ON_INTEL
    printf(" not");
#endif
    
    printf(" on an 64-bit Intel(R) platform.\n");
    printf("The used compiler is %s with version ", COMPILER);

#ifdef CLANG
    printf("%s\n", COMPILER_VERSION);
#else
    printf("%d\n", COMPILER_VERSION);
#endif
    return 0;
}
