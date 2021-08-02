import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

/**
 * Panama Hello World calling C functions.
 */
public class HelloWorld {
    public static void main(String[] args) {
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           MemorySegment str = CLinker.toCString("Hello World\n", scope);
           org.unix.stdio_h.printf(str);
       }
    }
}

