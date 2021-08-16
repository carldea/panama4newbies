import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.util.Random;

import static jdk.incubator.foreign.CLinker.*;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;


/**
 * Panama Pointers and Structs
 */
public class PointersAndStructs {
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

        System.out.println("\nCreate A Sequence of Point structs:");
        SequenceLayout seqStruct = MemoryLayout.sequenceLayout(5, pointStruct);
        MemorySegment points = allocator.allocate(seqStruct);

        var VHSeq_x = seqStruct.varHandle(int.class,
                MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("x"));
        var VHSeq_y = seqStruct.varHandle(int.class,
                MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("y"));


        Random random = new Random();
        for(long i=0; i<seqStruct.elementCount().getAsLong(); i++) {
          VHSeq_x.set(points, i, random.nextInt(100));
          VHSeq_y.set(points, i, random.nextInt(100));
        }
        for(long i=0; i<seqStruct.elementCount().getAsLong(); i++) {
          System.out.printf(" points[%d] = (%2d, %3d) \n", i, VHSeq_x.get(points, i), VHSeq_y.get(points, i));
        }
      }
    }
}

