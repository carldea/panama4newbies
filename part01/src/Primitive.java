
import static org.unix.stdio_h.*;


/// Primitive class creates a C double type (64 bits) off the Java heap. While this might be simple this example
/// shows you how to allocate a primitive in off heap memory and call C's printf() function.
/// Like the HelloWorld.java example to output a double the call to access the value as a double via
/// `cDouble.get(C_DOUBLE, 0)`
///

/// Main method entry point.
    void main() {
        try (Arena arena = Arena.ofConfined()) {
            var cFormatStr = arena.allocateFrom("A slice of %f \n");

            // This creates a double off heap (outside jvm)
            var cDouble = arena.allocateFrom(C_DOUBLE, Math.PI);

            // Creates a method handle to the printf( format C string, double value)
            var printfFun = printf.makeInvoker(C_DOUBLE); // 2nd param - defines the value type for %f

            // obtains the double off heap.
            printfFun.apply(cFormatStr, cDouble.get(C_DOUBLE, 0)); // A slice of 3.141593

            // but of course you can just do the following from Java (passing in the Java double value):
            printfFun.apply(cFormatStr, Math.PI);  // A slice of 3.141593

        }
    }