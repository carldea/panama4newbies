
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySession;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.ValueLayout.JAVA_INT;


/**
 * Panama Structs
 */
public class Structs {
    public static void main(String[] args) {
      try (var memorySession = MemorySession.openConfined()) {
        /*
            struct Point {
               int x;
               int y;
            };
        */
        System.out.println("\nCreate one Point struct:");
        GroupLayout pointStruct = MemoryLayout.structLayout(
                JAVA_INT.withName("x"),
                JAVA_INT.withName("y")
        );

        var cPoint = memorySession.allocate(pointStruct);
        VarHandle VHx = pointStruct.varHandle(MemoryLayout.PathElement.groupElement("x"));
        VarHandle VHy = pointStruct.varHandle(MemoryLayout.PathElement.groupElement("y"));
        VHx.set(cPoint, 100);
        VHy.set(cPoint, 200);


        System.out.printf("cPoint = (%d, %d) \n",  VHx.get(cPoint), VHy.get(cPoint));

//        var pCPoint = allocator.allocate(C_POINTER, cPoint);
//        System.out.printf("pCPoint = (%d) \n",  VHx.get(pCPoint.address()));
      }
    }
}

