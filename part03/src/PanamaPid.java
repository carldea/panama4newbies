import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySession;
import java.lang.invoke.*;
import org.unix.foo_h;

import static java.lang.foreign.ValueLayout.JAVA_INT;
import static org.unix.foo_h.C_INT;
import static org.unix.foo_h.printf;

/**
 * Panama 4 newbies demo of calling getpid() .
 * @author cdea
 */
public class PanamaPid {
    public static void main(String[] args) {
        // obtain a scope
        try (var memorySession = MemorySession.openConfined()) {

            var cLinker = Linker.nativeLinker();
            // Using a MethodHandle
            MethodHandle getpidMH = cLinker.downcallHandle(cLinker.defaultLookup().lookup("getpid").get(),
                    FunctionDescriptor.of(JAVA_INT));

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
