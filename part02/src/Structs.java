import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.util.Random;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;


/**
 * Panama Structs
 */
public class Structs {
    public static void main(String[] args) {
      try (var scope = newConfinedScope()) {
        var allocator = SegmentAllocator.ofScope(scope);

        /*
            struct Point {
               int x;
               int y;
            };
        */
        System.out.println("\nCreate one Point struct:");
        GroupLayout pointStruct = MemoryLayout.structLayout(
                C_INT.withName("x"),
                C_INT.withName("y")
        );

        var cPoint = allocator.allocate(pointStruct);
        VarHandle VHx = pointStruct.varHandle(int.class, MemoryLayout.PathElement.groupElement("x"));
        VarHandle VHy = pointStruct.varHandle(int.class, MemoryLayout.PathElement.groupElement("y"));
        VHx.set(cPoint, 100);
        VHy.set(cPoint, 200);

        System.out.printf("cPoint = (%d, %d) \n",  VHx.get(cPoint), VHy.get(cPoint));
      }
    }
}

