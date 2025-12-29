
import java.lang.foreign.*;
import java.util.Random;

import static java.lang.foreign.ValueLayout.JAVA_INT;

/// Panama StructsArray.java will create a Sequence of Point(x, y) structs.
///
/// This class makes use of the following:
/// 1. Markdown - Uses Markdown instead of older javadoc comments [(JEP 467)](https://openjdk.org/jeps/467)
/// 2. Compact classes - Concise Java code [(JEP 512)](https://openjdk.org/jeps/512)
/// 3. FFI & FFM - The final release of foreign function & memory API [(JEP 454)](https://openjdk.org/jeps/454)
///
///  The main entry point
void main() {
    try (var arena = Arena.ofConfined()) {
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
        MemorySegment points = arena.allocate(seqStruct);

        var VHSeq_x = seqStruct.varHandle(
                MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("x"));
        var VHSeq_y = seqStruct.varHandle(
                MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("y"));

        Random random = new Random();
        for(long i=0; i<seqStruct.elementCount(); i++) {
            VHSeq_x.set(points, 0L, i, random.nextInt(100));
            VHSeq_y.set(points, 0L, i, random.nextInt(100));
        }
        for(long i=0; i<seqStruct.elementCount(); i++) {
            System.out.printf(" points[%d] = (%2d, %3d) \n", i, VHSeq_x.get(points, 0L, i), VHSeq_y.get(points,  0L, i));
        }
    }
}


// +---------------------------------------------------------------------------------------------
// | Notes: New with var handles allows you to define a base offset into the sequence.
// |        Below is an example of a 16-byte header and a sequence of 5 structs.
// +---------------------------------------------------------------------------------------------
/*

// Total segment = 16-byte header + space for 5 Points
long baseOffset = 16L;
MemorySegment segment = arena.allocate(baseOffset + seqStruct.byteSize());

Random random = new Random();

for (long i = 0; i < seqStruct.elementCount(); i++) {
    // Each VHSeq call requires: [Segment, Base Offset, Index, Value]

    // Set the 'x' field of the i-th point
    VHSeq_x.set(segment, baseOffset, i, random.nextInt(100));

    // Set the 'y' field of the i-th point
    VHSeq_y.set(segment, baseOffset, i, random.nextInt(100));
}


// Alternate way to define the base offset prior to loop.
import java.lang.invoke.MethodHandles;

// 1. Setup the memory segment with 16 bytes of header space
long headerOffset = 16L;
MemorySegment segment = arena.allocate(headerOffset + seqStruct.byteSize());

// 2. Bind coordinate index 1 (the base offset) to 16L upfront
// This changes the signature from (Segment, long offset, long index, int)
// to (Segment, long index, int)
var VHSeq_x_header = MethodHandles.insertCoordinates(VHSeq_x, 1, headerOffset);
var VHSeq_y_header = MethodHandles.insertCoordinates(VHSeq_y, 1, headerOffset);

Random random = new Random();

// 3. Simplified loop: No need to pass 16L repeatedly
for (long i = 0; i < seqStruct.elementCount(); i++) {
    // Both calls now automatically start at byte 16 and add the index i
    VHSeq_x_header.set(segment, i, random.nextInt(100));
    VHSeq_y_header.set(segment, i, random.nextInt(100));
}
 */