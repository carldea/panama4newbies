#include <stdio.h>

struct Point {
  int x;
  int y;
} points[5];

int main () {
   struct Point pt;
   pt.x = 100;
   pt.y = 50;
   printf("Point pt = (%d, %d) \n",  pt.x, pt.y);

   for (int i=0; i<5; i++) {
     points[i].x = 100 + i;
     points[i].y = 200 + i;
   }

   for (int i=0; i<5; i++) {
     printf("Point pt = (%3d, %3d) \n",  points[i].x, points[i].y);
   }
}