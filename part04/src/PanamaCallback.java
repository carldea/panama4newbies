import org.unix.foo_h;
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import static org.unix.foo_h.*;

public class PanamaCallback {

    /**
     * C code will call this static method.
     */
    public static void callMePlease() {
        MemorySegment cString = Arena.ofAuto().allocateFrom("[JAVA] Inside callMePlease() method - I'm being called from C.\n");
        foo_h.printf.makeInvoker().apply(cString);
    }

    /**
     * C code will call this static method.
     * @param value C will pass in a number. Your code will double it.
     */
    public static void doubleMe(int value) {
        MemorySegment cString = Arena.ofAuto().allocateFrom("[JAVA] Inside doubleMe() method, %d times 2 = %d.\n".formatted(value, value*2));
        foo_h.printf.makeInvoker().apply(cString);
    }

    /**
     * Main entry point.
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        try (var arena = Arena.ofConfined()) {
            // MemorySegment C's printf using a C string
            MemorySegment cString = arena.allocateFrom("[Java] Callbacks! Panama style\n");

            foo_h.printf.makeInvoker().apply(cString);
            fflush(NULL());

            // 1. Obtain linker
            var linker = Linker.nativeLinker();

            // 2. Obtaining symbol lookup from two places.
            SymbolLookup foolibLookup = SymbolLookup.libraryLookup(System.mapLibraryName("mylib"),  Arena.ofAuto())
                    .or(SymbolLookup.loaderLookup())
                    .or(Linker.nativeLinker().defaultLookup());
//            SymbolLookup stdlibLookup = SymbolLookup.loaderLookup()
//                    .or(linker.defaultLookup());

            // my_callback_function C function receives a callback (pointer to a function)
            MemorySegment callback1 = foolibLookup.findOrThrow("my_callback_function");
            var my_callback_functionMethodHandle = linker.downcallHandle(
                    callback1,
                    FunctionDescriptor.ofVoid(C_POINTER)
            );

            // my_callback_function2 C function receives a callback (pointer to a function)
            MemorySegment callback2 = foolibLookup.findOrThrow("my_callback_function2");
            var my_callback_function2MethodHandle = linker.downcallHandle(
                    callback2,
                    FunctionDescriptor.ofVoid(C_POINTER, C_INT)
            );

            // Create a method handle to the Java function as a callback
            MethodHandle onCallMePlease = MethodHandles.lookup()
                    .findStatic(PanamaCallback.class,
                            "callMePlease",
                            MethodType.methodType(void.class));

            // Create a stub as a native symbol to be passed into native function.
            // void (*ptr)()
            MemorySegment callMePleaseNativeSymbol = linker.upcallStub(
                    onCallMePlease,
                    FunctionDescriptor.ofVoid(),
                    arena);

            // Invoke C function receiving a callback
            // void my_callback_function(void (*ptr)())
            my_callback_functionMethodHandle.invokeExact(callMePleaseNativeSymbol);

            // Create a method handle to the Java function as a callback
            MethodHandle onDoubleMe = MethodHandles.lookup()
                    .findStatic(PanamaCallback.class,
                            "doubleMe",
                            MethodType.methodType(void.class, int.class));

            // Create a stub as a native symbol to be passed into native function.
            // void (*ptr)(int)
            MemorySegment doubleMeNativeSymbol = Linker.nativeLinker().upcallStub(
                    onDoubleMe,
                    FunctionDescriptor.ofVoid(C_INT),
                    arena);

            // Invoke C function receiving a callback
            // void my_callback_function2(void (*ptr)(int))
            foo_h.my_callback_function2(doubleMeNativeSymbol);
        }
    }
}
