// sayhello.cpp
#include "sayhello.h"
#include <stdio.h>

// only "extern" when targeting C.
extern "C" void say_hello();

extern void c_say_hello() {
   say_hello(); // calling swift
}

extern int main(){
    say_hello();    // Prints "Hello, World!"
    return 0;
}
