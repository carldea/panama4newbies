import jdk.incubator.foreign.*;
import java.util.Date;

import static jdk.incubator.foreign.CLinker.*;
import static org.unix.foo_h.*;
import org.unix.tm;

/**
 * Panama 4 newbies demo of calling time.h functions.
 * @author cdea
 */
public class PanamaTime {
    public static void main(String[] args) {
        // obtain a scope
        try (var scope = ResourceScope.newConfinedScope()) {
            var allocator = SegmentAllocator.ofScope(scope);

            // The variable now of type MemorySegment holds a C long type. The allocate method will create space.
            // time_t * (clong seconds since epoch) probable type is from a typedef long time_t; (64 bit)

            // Populate now variable with a Java epoch seconds. C long
            var now = allocator.allocate(C_LONG, System.currentTimeMillis() / 1000);

            // Equivalent because time_t is a C_LONG
            var now2 = allocator.allocate(time_t);

            // Get contents of now (java epoch seconds)
            long secondsSinceEpoch1 = MemoryAccess.getLong(now);

            // Populate now2 with (C's epoch seconds) and returns as a Java long.
            long secondsSinceEpoch2 = time(now2);

            // Get contents of now2 (C's epoch seconds) return as a Java long.
            long secondsSinceEpoch3 = MemoryAccess.getLong(now2);

            assert secondsSinceEpoch1 == secondsSinceEpoch2 && secondsSinceEpoch2 == secondsSinceEpoch3;

            // Grab epoch time in seconds, then convert to milliseconds.
            System.out.printf("1. Java DateTime from C time function: %s\n", new Date(secondsSinceEpoch2 * 1000));

            // tm is a C struct with an allocate method to create space
            // by instantiating a MemorySegment (pointer to tm struct)
            // struct tm *
            MemorySegment pTmStruct = tm.allocate(scope);

            // Calling the C function localtime_r(now, time); now contains seconds, and time is a blank struct.
            // [ptr to struct]        [ptr to time_t           ]   [ptr to tm struct     ]
            // struct tm *localtime_r( const time_t * epochSeconds, struct tm * tmStruct );
            localtime_r(now2, pTmStruct);

            // Using pointer offsets to grab integers in memory (see struct in comments at the end). 4 bytes
            var secondsCInt = MemoryAccess.getIntAtOffset(pTmStruct, 0);
            var minuteCInt = MemoryAccess.getIntAtOffset(pTmStruct, 4);
            var hourCInt = MemoryAccess.getIntAtOffset(pTmStruct, 8);

            var cString = toCString("2. C's printf & tm Struct of local time. %02d:%02d:%02d\n", scope);
            printf(cString, hourCInt, minuteCInt, secondsCInt);
            fflush(__stdoutp$get());

            // After invocation the time struct will
            System.out.printf("3. C's tm struct getters tm_hour, tm_min, tm_sec. %02d:%02d:%02d\n",
                    tm.tm_hour$get(pTmStruct), tm.tm_min$get(pTmStruct), tm.tm_sec$get(pTmStruct));

            // Call time.h asctime() function to display date time.
            printf(toCString("4. C's asctime() function to display date time: %s\n", scope), asctime(pTmStruct));
        }
    }
}

// An excerpt from time.h
/*
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