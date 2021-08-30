import java.lang.invoke.*;
import jdk.incubator.foreign.*;
import org.unix.foo_h;

import static jdk.incubator.foreign.CLinker.C_INT;

/**
 * Panama 4 newbies demo of calling getpid() .
 * @author cdea
 */
public class PanamaPid {
    public static void main(String[] args) {
        // obtain a scope
        try (var scope = ResourceScope.newConfinedScope()) {
            var allocator = SegmentAllocator.ofScope(scope);

            // Using a MethodHandle
            MethodHandle getpidMH = CLinker.getInstance()
                    .downcallHandle( CLinker.systemLookup().lookup("getpid").get(),
                    allocator,
                    MethodType.methodType(int.class), // first argument is the return type in java. second argument is parameters
                    FunctionDescriptor.of(C_INT)      // first argument is the return type in C. second arg is parameters.
            );
            int pid	= (int) getpidMH.invokeExact();
            System.out.printf("MethodHandle calling getpid() (%d)\n", pid);

            // Using Jextract's getpid method.
            int jextractPid = foo_h.getpid();
            System.out.printf("Jextract's calling getpid()   (%d)\n", jextractPid);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
