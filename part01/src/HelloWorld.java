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
           MemorySegment cString = toCString("Hello World! Panama style\n", scope);

           // int printf(const char *format, ...);  a variadic function
           printf(cString);
           fflush(__stdoutp$get());
       }
    }
}

