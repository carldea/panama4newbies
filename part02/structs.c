#include <stdio.h>

struct Point {
  int x;
  int y;
};

int main () {
   struct Point pt;
   pt.x = 100;
   pt.y = 50;
   printf("Point pt = (%d, %d) \n",  pt.x, pt.y);
}