
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static java.lang.System.out;
import static java.lang.foreign.ValueLayout.JAVA_INT;

/// Pointers.java Will demonstrate what the C language concept of Pointers or addresses in memory.
///
/// This class makes use of the following:
/// 1. Markdown - Uses Markdown instead of older javadoc comments [(JEP 467)](https://openjdk.org/jeps/467)
/// 2. Compact classes - Concise Java code [(JEP 512)](https://openjdk.org/jeps/512)
/// 3. FFI & FFM - The final release of foreign function & memory API [(JEP 454)](https://openjdk.org/jeps/454)
///
/// Pointers's entry point main method using a concise version. Notice Pointers class declaration is absent.
void main() {
    try (var arena = Arena.ofConfined()) {
        out.println("\nCreating Pointers:");

        // int x = 5;
        var x = arena.allocateFrom(JAVA_INT, 5);

        // int *ptr;
        long address = x.address();             // obtain address

        // ptr = &x;
        long ptr = address;

        // Create a memory segment at ptr's address and reinterpret size as 4 bytes (primitive int).
        MemorySegment ptrMemSeg =  MemorySegment.ofAddress(ptr).reinterpret(4);

        // Output value: x = 5 and ptr's value = 5
        out.printf("           x = %d    address = %x %n", x.get(JAVA_INT, 0), x.address());
        out.printf(" ptr's value = %d    address = %x %n", ptrMemSeg.get(JAVA_INT, 0), ptrMemSeg.address());

         // Change x = 10;
        x.set(JAVA_INT, 0, 10);
        out.printf(" Changing x's value to: %d %n", x.get(JAVA_INT, 0));

        // Output after change
        out.printf("           x = %d    address = %x %n", x.get(JAVA_INT, 0), x.address());
        out.printf(" ptr's value = %d    address = %x %n", ptrMemSeg.get(JAVA_INT, 0), ptrMemSeg.address());
    }
}