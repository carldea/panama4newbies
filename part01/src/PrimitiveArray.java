import jdk.incubator.foreign.*;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;
import static org.unix.stdio_h.__stdoutp$get;
import static org.unix.stdio_h.fflush;
import static org.unix.stdio_h.printf;

/**
 * Primitive Arrays of floating point values (C double).
 */
public class PrimitiveArray {
    public static void main(String[] args) {
       try (var scope = newConfinedScope()) {
           var allocator = SegmentAllocator.ofScope(scope);
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
           System.out.println();
       }
    }
}
