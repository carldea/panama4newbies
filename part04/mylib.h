#include <stdio.h>

void my_function();
void my_callback_function(void (*ptrToFunction)());
void my_callback_function2(void (*ptrToFunction)(int));