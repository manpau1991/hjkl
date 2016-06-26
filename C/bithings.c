/* Useful bit manipulation things. (c) Thomas Lang, 2016. -*- c -*- */

#include <stdio.h>

/* The following 3 macros work on INTEGERS only (because they use their bit representation). */

/* Get minimum/maximum without branching. */
#define MIN(x, y) (y ^ ((x ^ y) & -(x < y)))
#define MAX(x, y) (x ^ ((x ^ y) & -(x < y)))
/* Swap values without temporary variable. */
#define SWAP(a, b) (((a) ^= (b)), ((b) ^= (a)), ((a) ^= (b)))


#define NEGATE 0x01
#define PRINT  0x10

void process(int* number, const int mask)
{
    if (mask & NEGATE) {
        if (number) {
            *number = -(*number);

            if (mask & PRINT)
                printf("\033[1;33mNegated number.\033[0m\n");
        }
    }

    if (mask & PRINT)
        printf("\033[1;32mPrint called.\033[0m\n");
}

int main(int argc, char* argv[])
{
    int x = 3, y = 4;
    printf("The minimum of %d and %d is %d.\n", x, y, MIN(x, y));
    printf("The maximum of %d and %d is %d.\n", x, y, MAX(x, y));
    printf("%d and %d after swapping: ", x, y);
    SWAP(x, y);
    printf("%d and %d.\n", x, y);

    printf("----------------------------------------\n");

    x = 3;
    int x_ = x;
    printf("x before processing:                % 02d\n", x_);
    printf("----------------------------------------\n");
    
    process(&x_, 0);
    printf("x after process(x, 0):              % 02d\n", x_);
    printf("----------------------------------------\n");
    x_ = x;
    
    process(&x_, NEGATE);
    printf("x after process(x, NEGATE):         % 02d\n", x_);
    printf("----------------------------------------\n");
    x_ = x;

    process(&x_, PRINT);
    printf("x after process(x, PRINT):          % 02d\n", x_);
    printf("----------------------------------------\n");
    x_ = x;

    process(&x_, NEGATE | PRINT);
    printf("x after process(x, NEGATE | PRINT): % 02d\n", x_);
    printf("----------------------------------------\n");
    return 0;
}
