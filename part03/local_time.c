#include <stdio.h>
#include <time.h>

int main () {

    time_t epochSeconds;
    epochSeconds = time(NULL);           // Result is a long of seconds since epoch
    struct tm *ptrTmStruct, tmStruct;    // Create a pointer to a tm struct
    ptrTmStruct = &tmStruct;             // assign ptr to empty struct

    // C's localtime_r() function definition
    // struct tm *localtime_r(const time_t * __restrict, struct tm * __restrict);

    // populates tm struct
    localtime_r(&epochSeconds, ptrTmStruct);

    printf("A C program to display local date time.\n");

    // display as local time, and epoch seconds.
    printf("  Seconds since the epoch: %ld\n",   epochSeconds);
    printf("  Local Time: %s",  asctime(ptrTmStruct));


    return 0;
}

/*
// An excerpt from time.h
struct tm {
   int tm_sec;         // seconds,  range 0 to 59
   int tm_min;         // minutes, range 0 to 59
   int tm_hour;        // hours, range 0 to 23
   int tm_mday;        // day of the month, range 1 to 31
   int tm_mon;         // month, range 0 to 11
   int tm_year;        // The number of years since 1900
   int tm_wday;        // day of the week, range 0 to 6
   int tm_yday;        // day in the year, range 0 to 365
   int tm_isdst;       // daylight saving time
};

*/