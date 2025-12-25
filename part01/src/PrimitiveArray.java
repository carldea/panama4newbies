

import static java.lang.IO.println;
import static java.lang.System.out;
import static org.unix.stdio_h.*;

/// Primitive Arrays of floating point values (C double). This demonstrates how to allocate/and set an array of doubles
/// off heap.
///
/// This class makes use of the following:
/// 1. Markdown - Uses Markdown instead of older javadoc comments [(JEP 467)](https://openjdk.org/jeps/467)
/// 2. Compact classes - Concise Java code [(JEP 512)](https://openjdk.org/jeps/512)
/// 3. FFI & FFM - The final release of foreign function & memory API [(JEP 454)](https://openjdk.org/jeps/454)
/// 4. jextract - Uses jextract jdk 25+ [jextract releases](https://jdk.java.net/jextract/) [sources](https://github.com/openjdk/jextract)
///

    void main() {
        try (Arena arena = Arena.ofConfined()) {
           println("An array of data");
           MemorySegment cDoubleArray = arena.allocateFrom(C_DOUBLE,
                   1.0, 2.0, 3.0, 4.0,
                   1.0, 1.0, 1.0, 1.0,
                   3.0, 4.0, 5.0, 6.0,
                   5.0, 6.0, 7.0, 8.0);

            logInfo(cDoubleArray);

            // multiply by 3
            for (long i = 0; i < 16; i++) {
                double newVal = cDoubleArray.getAtIndex(C_DOUBLE, i) * 3;
                cDoubleArray.setAtIndex(C_DOUBLE, i, newVal);
            }
            logInfo(cDoubleArray);
        }
    }

private static void logInfo(MemorySegment cDoubleArray) {
    for (long i = 0; i < 16; i++) {
        if (i>0 && i % 4 == 0) {
            println();
        }
        out.printf(" %f ", cDoubleArray.getAtIndex(C_DOUBLE, i ));
    }
    println();
    println();
}