import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.util.Random;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;


/**
 * Panama Pointers
 */
public class Pointers {
    public static void main(String[] args) {
      try (var scope = newConfinedScope()) {
        System.out.println("\nCreating Pointers:");
        var allocator = SegmentAllocator.ofScope(scope);

        // int x = 5;
        var x = allocator.allocate(C_INT, 5);

        // int *ptr;
        MemoryAddress address = x.address();             // obtain address

        // ptr = &x;
        MemorySegment ptr = address.asSegment(4, scope); // segment is 4 bytes long

        // Output value: x = 5 and ptr's value = 5
        System.out.printf("           x = %d    address = %x %n", MemoryAccess.getInt(x), x.address().toRawLongValue());
        System.out.printf(" ptr's value = %d    address = %x %n", MemoryAccess.getInt(ptr), ptr.address().toRawLongValue());

         // Change x = 10;
        MemoryAccess.setInt(x, 10);
        System.out.printf(" Changing x's value to: %d %n", MemoryAccess.getInt(x));

        // Output after change
        System.out.printf("           x = %d    address = %x %n", MemoryAccess.getInt(x), x.address().toRawLongValue());
        System.out.printf(" ptr's value = %d    address = %x %n", MemoryAccess.getInt(ptr), ptr.address().toRawLongValue());
      }
   }
}

