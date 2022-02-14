#include <stdio.h>
#include "mylib.h"

void my_function() {
   printf("This is a normal function.");
}

void my_callback_function(void (*ptrToFunction)()) {
   printf("[C] Inside mydb's C function my_callback_function().\n");
   printf("[C]   Now invoking Java's callMePlease() static method.\n");

   // Calling the passed in callback
   (*ptrToFunction)();
}

void my_callback_function2(void (*ptrToFunction)(int)) {
   printf("[C] Inside mydb's C function my_callback_function2().\n");
   printf("[C]   Now invoking Java's doubleMe(int) static method.\n");
   int x = 123;
   (*ptrToFunction)(x);   //calling the callback function
}

int main() {
   printf("[C] Callbacks! \n");
   void (*ptr)() = &my_function;
   my_callback_function(ptr);
   return 0;
}