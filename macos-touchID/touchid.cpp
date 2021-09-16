// touchid.cpp
#include "touchid.h"
#include <stdio.h>

// only "extern" when targeting C. // swift's method name from @_cdecl("authenticate_user")
extern "C" void authenticate_user();

extern void c_authenticate_user() {
   authenticate_user(); // calling swift
}

