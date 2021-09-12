import jdk.incubator.foreign.*;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;
import static org.unix.stdio_h.__stdoutp$get;
import static org.unix.stdio_h.fflush;
import static org.unix.stdio_h.printf;

/**
 * Creating primitive values such as a C double.
 */
public class Primitive {
    public static void main(String[] args) {
       try (var scope = newConfinedScope()) {
           // Creating a C double
           var allocator = SegmentAllocator.ofScope(scope);
           var cDouble = allocator.allocate(C_DOUBLE, Math.PI);
           printf(toCString("A slice of %f \n", scope), MemoryAccess.getDouble(cDouble));
       }
    }
}

