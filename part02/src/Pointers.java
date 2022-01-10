import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.util.Random;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;
import static org.unix.stdio_h.C_INT;
import static org.unix.stdio_h.C_POINTER;


/**
 * Panama Pointers
 */
public class Pointers {
    public static void main(String[] args) {
      try (var scope = newConfinedScope()) {
        System.out.println("\nCreating Pointers:");
        var allocator = SegmentAllocator.implicitAllocator();

        // int x = 5;
        var x = allocator.allocate(C_INT, 5);

        // int *ptr;
        MemoryAddress address = x.address();             // obtain address

        // ptr = &x;
        MemoryAddress ptr = address;
        //MemorySegment ptrVal = MemorySegment.ofAddress(ptr, 8, scope);
        // Output value: x = 5 and ptr's value = 5
        System.out.printf("           x = %d    address = %x %n", x.get(C_INT, 0), x.address().toRawLongValue());
        System.out.printf(" ptr's value = %d    address = %x %n", ptr.get(C_INT, 0), ptr.address().toRawLongValue());
//        System.out.printf(" ptr's value = %d    address = %x %n", ptrVal.get(C_INT, 0), ptrVal.address().toRawLongValue());

         // Change x = 10;
        x.set(C_INT, 0, 10);
        System.out.printf(" Changing x's value to: %d %n", x.get(C_INT, 0));

        // Output after change
        System.out.printf("           x = %d    address = %x %n", x.get(C_INT, 0), x.address().toRawLongValue());
        System.out.printf(" ptr's value = %d    address = %x %n", ptr.get(C_INT, 0), ptr.address().toRawLongValue());
      }
   }
}

