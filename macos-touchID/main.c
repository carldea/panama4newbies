#include <stdint.h>
#include <stdio.h>

intptr_t mymodule_foo(intptr_t);

intptr_t invoke_foo(intptr_t x) {
  return mymodule_foo(x);
}
int main() {
  printf(" %ld", invoke_foo(5l));  
  return (0);
}
