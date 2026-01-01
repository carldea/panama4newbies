import org.unix.foolib;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.invoke.*;

/**
 * Panama 4 newbies demo of calling getpid() .
 * @author cdea
 */
void main() {
    // obtain a scope
    try (var  arena = Arena.ofConfined()) {

        // 1. Obtain linker
        var linker = Linker.nativeLinker();

        // 2. Obtaining symbol lookup from two places.
        SymbolLookup stdlibLookup = SymbolLookup.loaderLookup()
                .or(linker.defaultLookup());

        // 3. Obtain symbol
        MemorySegment getpidSymbol = stdlibLookup.findOrThrow("getpid");

        // 4. Define the functions return type and argument signature. e.g. int getpid()
        FunctionDescriptor funcDef = FunctionDescriptor.of(ValueLayout.JAVA_INT); /* return type (int)  */

        // 5. Create a method handle to native function
        MethodHandle getpidMethodHandle = linker.downcallHandle(getpidSymbol, funcDef);

        int pid	= (int) getpidMethodHandle.invokeExact();
        System.out.printf("MethodHandle calling getpid() (%d)\n", pid);

        // Using Jextract's getpid method.
        int jextractPid = foolib.getpid();
        System.out.printf("Calling getpid()   (%d)\n", jextractPid);

    } catch (Throwable throwable) {
        throwable.printStackTrace();
    }
}