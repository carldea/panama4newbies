#include <stdio.h>

int doubleIt(int *a);
int main () {
   int x = 5;
   int *ptr; // 1. Declare a pointer of type int.
   ptr = &x; // 2. Assign pointer variable to the address of x.

   // Display locations in memory
   printf("                                    Address of x variable: %x\n", &x );
   printf("                           Address stored in ptr variable: %x\n", ptr );

   // Call doubleIt() by reference
   printf("               Address of the variable x. Call doubleIt(): %d\n", doubleIt(&x) );
   printf("Pointer to the address of the variable x. Call doubleIt(): %d\n", doubleIt(ptr) );

}
/**
 * Returns a value doubled.
 * @param *a pointer to an int
 * @return int doubling of a value.
 */
int doubleIt(int *a) {
   return 2 * (*a); // two times the value at address (of pointer a).
}