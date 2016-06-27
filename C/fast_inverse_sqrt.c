/* Fast inverse square root and damn evil bit hacking. 2016, -*- c -*- */

/*
 * A very crazy function for fast computing of the reciproc square root
 * of the passed number.
 *
 * This piece of code was passed to us from the mysterious gods who created
 * the one and only Quake III engine. These divine beings really seem to 
 * understand how bit hacking works.
 * So, for the glory of low-level code in our high-level world today, praise
 * and trust these engineers for granting their knowledge with us.
 * 
 * (Needed for the very naive implementation).
 *
 * @param number The number whose reciproc square root to compute.
 * @return Returns the reciproc square root of the passed number.
 */
#ifdef __x86_64__
double sqrt_recip(double number)
{
    const double three_halfs = 1.5;
    double x2 = number * 0.5;
    double y  = number;
    long i = *(long*) &y; /* damn evil bit hacking */
    i = 0x5fe6eb50c7b537a9 - (i >> 1); /* obvious, isn't it? */

    /*
     * Now we choose a good starting value and perform two
     * iterations of Newton's method to find the fixed point.
     * Because of limited accuracy of hardware, we only need
     * two such iterations.
     */
    y = * (double*) &i;
    y = y * (three_halfs - (x2 * y * y));
    y = y * (three_halfs - (x2 * y * y));
    return y;
}

#else

float sqrt_recip(float number)
{
    const float three_halfs = 1.5;
    float x2 = number * 0.5;
    float y  = number;
    long i = *(long*) &y; /* damn evil bit hacking */
    i = 0x5f375a86 - (i >> 1); /* obvious, isn't it? */

    /*
     * Now we choose a good starting value and perform two
     * iterations of Newton's method to find the fixed point.
     * Because of limited accuracy of hardware, we only need
     * two such iterations.
     */
    y = * (float*) &i;
    y = y * (three_halfs - (x2 * y * y));
    y = y * (three_halfs - (x2 * y * y));
    return y;
}

#endif
