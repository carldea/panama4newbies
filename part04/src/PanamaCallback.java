import jdk.incubator.foreign.*;
import org.unix.foo_h;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import static jdk.incubator.foreign.ResourceScope.newConfinedScope;
import static jdk.incubator.foreign.SegmentAllocator.implicitAllocator;
import static org.unix.foo_h.*;

public class PanamaCallback {

    /**
     * C code will call this static method.
     */
    public static void callMePlease() {
        MemorySegment cString = implicitAllocator()
                .allocateUtf8String("[JAVA] Inside callMePlease() method - I'm being called from C.\n");
        foo_h.printf(cString);
    }

    /**
     * C code will call this static method.
     * @param value C will pass in a number. Your code will double it.
     */
    public static void doubleMe(int value) {
        MemorySegment cString = implicitAllocator()
                .allocateUtf8String("[JAVA] Inside doubleMe() method, %d times 2 = %d.\n".formatted(value, value*2));
        foo_h.printf(cString);
    }

    /**
     * Main entry point.
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        try (var scope = newConfinedScope()) {
            // MemorySegment C's printf using a C string
            MemorySegment cString = implicitAllocator()
                    .allocateUtf8String("[Java] Callbacks! Panama style\n");

            printf(cString);
            fflush(__stdoutp$get());
            //fprintf(__stdoutp$get(), cString);

            // my_callback_function C function receives a callback (pointer to a function)
            NativeSymbol callback1 = SymbolLookup.loaderLookup().lookup("my_callback_function").get();
            var my_callback_functionMethodHandle = CLinker.systemCLinker().downcallHandle(
                    callback1,
                    FunctionDescriptor.ofVoid(C_POINTER)
            );

            // my_callback_function2 C function receives a callback (pointer to a function)
            NativeSymbol callback2 = SymbolLookup.loaderLookup().lookup("my_callback_function2").get();
            var my_callback_function2MethodHandle = CLinker.systemCLinker().downcallHandle(
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
            NativeSymbol callMePleaseNativeSymbol = CLinker.systemCLinker().upcallStub(
                    onCallMePlease,
                    FunctionDescriptor.ofVoid(),
                    scope);

            // Invoke C function receiving a callback
            // void my_callback_function(void (*ptr)())
            my_callback_functionMethodHandle.invokeExact((Addressable) callMePleaseNativeSymbol);

            // Create a method handle to the Java function as a callback
            MethodHandle onDoubleMe = MethodHandles.lookup()
                    .findStatic(PanamaCallback.class,
                            "doubleMe",
                            MethodType.methodType(void.class, int.class));

            // Create a stub as a native symbol to be passed into native function.
            // void (*ptr)(int)
            NativeSymbol doubleMeNativeSymbol = CLinker.systemCLinker().upcallStub(
                    onDoubleMe,
                    FunctionDescriptor.ofVoid(C_INT),
                    scope);

            // Invoke C function receiving a callback
            // void my_callback_function2(void (*ptr)(int))
            foo_h.my_callback_function2(doubleMeNativeSymbol);
        }
    }
}
