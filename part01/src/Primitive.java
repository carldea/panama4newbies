
import java.lang.foreign.MemorySession;
import static org.unix.stdio_h.*;

/**
 * Creating primitive values such as a C double.
 */
public class Primitive {
    public static void main(String[] args) {
       try (var memorySession = MemorySession.openConfined()) {
           // Creating a C double
           var cDouble = memorySession.allocate(C_DOUBLE, Math.PI);
           var msgStr = memorySession.allocateUtf8String("A slice of %f \n");
           printf(msgStr, cDouble.get(C_DOUBLE, 0));
       }
    }
}

