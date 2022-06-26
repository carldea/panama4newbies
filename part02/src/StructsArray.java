
import java.lang.foreign.*;
import java.util.Random;

import static java.lang.foreign.ValueLayout.JAVA_INT;

/**
 * Panama Structs Array
 */
public class StructsArray {
    public static void main(String[] args) {
      try (var memorySession = MemorySession.openConfined()) {
        /*
            struct Point {
               int x;
               int y;
            };
        */
        GroupLayout pointStruct = MemoryLayout.structLayout(
                JAVA_INT.withName("x"),
                JAVA_INT.withName("y")
        );

        System.out.println("\nCreate A Sequence of Point structs:");
        SequenceLayout seqStruct = MemoryLayout.sequenceLayout(5, pointStruct);
        MemorySegment points = memorySession.allocate(seqStruct);

        var VHSeq_x = seqStruct.varHandle(
                MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("x"));
        var VHSeq_y = seqStruct.varHandle(
                MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("y"));


        Random random = new Random();
        for(long i=0; i<seqStruct.elementCount(); i++) {
          VHSeq_x.set(points, i, random.nextInt(100));
          VHSeq_y.set(points, i, random.nextInt(100));
        }
        for(long i=0; i<seqStruct.elementCount(); i++) {
          System.out.printf(" points[%d] = (%2d, %3d) \n", i, VHSeq_x.get(points, i), VHSeq_y.get(points, i));
        }
      }
    }
}