#include <iostream>

// The FizzBuzz program at compile time.

static std::string fb(const int N) {
    if (N % 3 == 0 && N % 5 == 0)
        return "FizzBuzz";
    else if (N % 3 == 0)
        return "Fizz";
    else if (N % 5 == 0)
        return "Buzz";
    else
        return std::to_string(N);
}

// yes, no security check if N < 0, because hardcoded at compile time ^^
template<int N>
struct FizzBuzz {
    static void Do() {
        std::cout << fb(N) << std::endl;
        FizzBuzz<N + 1>::Do();
    }
};

template<>
struct FizzBuzz<100> {
    static void Do() {
        std::cout << fb(100) << std::endl;
    }
};


int main(void) {
    FizzBuzz<1>::Do();
    return 0;
}
