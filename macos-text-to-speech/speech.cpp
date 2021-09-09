// speech.cpp
#include "speech.h"
#include <stdio.h>

// only "extern" when targeting C. // swift's method name from @_cdecl("authenticate_user")
extern "C" void say_something(char* name);

extern void c_say_something(char* name) {
   say_something(name); // calling swift
}

extern int main(){
    //say_something();
    return 0;
}
