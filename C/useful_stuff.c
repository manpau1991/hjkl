/* Just some usefull stuff one can need ... */
/* (c) Thomas Lang, 2016 */

#include <stdio.h>

/* Stuff 1: Check if compiler supports C++-style comments. */
#define HELPER 0//**/
#define CPP_COMMENTS_SUPPORTED (HELPER+1)


/* Stuff 2: 'goes-towards' operator */
void printThem(const char* const message, int count) {
    while (count --> 0) /* works because 0 is false, every other val is true */
        printf("%s\n", message);
}



#define PRINT_ME printf("In unrolled loop ...\n")

/* Stuff 3: loop unrolling using Duff's device. */
void unrollLoop(void) {
    /*
     * Unroll the following loop by 4:
     *
     * for (unsigned i = 0; i < 16; ++i)
     *     PRINT_ME;
     */
    int i = 16;
    int n = 4; /* == (16 + 3) / 4 */

    /* note: i & 3 == i % 4 */
    switch (i & 3) {
        case 0: do { PRINT_ME;
        case 3:      PRINT_ME;
        case 2:      PRINT_ME;
        case 1:      PRINT_ME;
                } while (--n > 0);
    }
}


// This pragma performs a simple barrier by telling
// the compiler a volatile inline-assembly statement
// which will not be optimized away. But we tell that
// "memory" has changed, so a barrier will be made.
#define BARRIER __asm__ __volatile__ ("": : :"memory");


int main(int argc, char** argv) {
#if CPP_COMMENTS_SUPPORTED
    printf("C++ comments are supported.\n\n");
#endif

    printThem("Nice operator for while condition!", 7);

    printf("\n");

    unrollLoop();

    BARRIER
    return 0;
}
