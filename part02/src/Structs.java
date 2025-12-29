
import java.lang.foreign.Arena;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.invoke.VarHandle;

import static java.lang.System.out;
import static java.lang.foreign.ValueLayout.JAVA_INT;


/**
 * Panama Structs will create a Point (x, y) struct containing int values.
 */
void main() {
    try (var arena = Arena.ofConfined()) {
        /*
            struct Point {
               int x;
               int y;
            };
        */
        out.println("\nCreate one Point struct:");
        GroupLayout pointStruct = MemoryLayout.structLayout(
                JAVA_INT.withName("x"),
                JAVA_INT.withName("y")
        );

        var cPoint = arena.allocate(pointStruct);
        VarHandle VHx = pointStruct.varHandle(MemoryLayout.PathElement.groupElement("x"));
        VarHandle VHy = pointStruct.varHandle(MemoryLayout.PathElement.groupElement("y"));
        VHx.set(cPoint, 0L, 100);
        VHy.set(cPoint, 0L, 200);


        out.printf("cPoint = (%d, %d) \n",  VHx.get(cPoint, 0L), VHy.get(cPoint, 0L)); // cPoint = (100, 200)

    }
}

