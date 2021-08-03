import jdk.incubator.foreign.*;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;
import static org.unix.stdio_h.__stdoutp$get;
import static org.unix.stdio_h.fflush;
import static org.unix.stdio_h.printf;

/**
 * Panama Hello World calling C functions.
 */
public class HelloWorld {
    public static void main(String[] args) {
       try (var scope = newConfinedScope()) {
           // MemorySegment C's printf using a C string
           var cString = toCString("Hello World\n", scope);
           // int printf(const char *format, ...);  a variadic function
           printf(cString);
           fflush(__stdoutp$get());

           // MemorySegment C's string to Java String using Java's printf
           var cString2 = toCString("Panama", scope);
           String string2 = toJavaString(cString2);
           System.out.printf("Hello, %s from a C string. \n", string2);

           // Creating a C double
           var allocator = SegmentAllocator.ofScope(scope);
           MemorySegment cDouble = allocator.allocate(C_DOUBLE, Math.PI);
           printf(toCString("A slice of %f \n", scope), MemoryAccess.getDouble(cDouble));
           fflush(__stdoutp$get());

           System.out.println("An array of data");
           MemorySegment cDoubleArray = allocator.allocateArray(C_DOUBLE, new double[] {
                   1.0, 2.0, 3.0, 4.0,
                   1.0, 1.0, 1.0, 1.0,
                   3.0, 4.0, 5.0, 6.0,
                   5.0, 6.0, 7.0, 8.0
           });

           for (int i = 0; i < (4*4); i++) {
               if (i>0 && i % 4 == 0) {
                   System.out.println();
               }
               System.out.printf(" %f ", MemoryAccess.getDoubleAtIndex(cDoubleArray, i));
           }
       }
    }
}

