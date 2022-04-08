import java.lang.invoke.*;
import jdk.incubator.foreign.*;
import org.unix.foo_h;

import static org.unix.foo_h.C_INT;

/**
 * Panama 4 newbies demo of calling getpid() .
 * @author cdea
 */
public class PanamaPid {
    public static void main(String[] args) {
        // obtain a scope
        try (var scope = ResourceScope.newConfinedScope()) {
            var allocator = SegmentAllocator.implicitAllocator();

            var cLinker = CLinker.systemCLinker();
            // Using a MethodHandle
            MethodHandle getpidMH = cLinker.downcallHandle(cLinker.lookup("getpid").get(),
                    FunctionDescriptor.of(C_INT));

            int pid	= (int) getpidMH.invokeExact();
            System.out.printf("MethodHandle calling getpid() (%d)\n", pid);

            // Using Jextract's getpid method.
            int jextractPid = foo_h.getpid();
            System.out.printf("Calling getpid()   (%d)\n", jextractPid);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
