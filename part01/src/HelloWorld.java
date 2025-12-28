import org.unix.stdio_h;
import static java.lang.IO.println;
import static org.unix.stdio_h.*;

/// Panama Hello World calling C stdio.h functions printf() and fflush().
/// This class makes use of the following:
/// 1. Markdown - Uses Markdown instead of older javadoc comments [(JEP 467)](https://openjdk.org/jeps/467)
/// 2. Compact classes - Concise Java code [(JEP 512)](https://openjdk.org/jeps/512)
/// 3. FFI & FFM - The final release of foreign function & memory API [(JEP 454)](https://openjdk.org/jeps/454)
/// 4. jextract - Uses jextract jdk 25+ [jextract releases](https://jdk.java.net/jextract/) [sources](https://github.com/openjdk/jextract)
///
/// Hello World's entry point main method using a concise version. Notice HelloWorld class declaration is absent.
void main() {
    // Use a confined arena for deterministic memory management
    try (Arena arena = Arena.ofConfined()) {
        // MemorySegment C's printf using a C string
        MemorySegment cString = arena.allocateFrom("Hello World! Panama style\n");

        // C language's printf() function: int printf(const char *format, ...);  a variadic function
        // ***********************************************************************
        // * How to call C's printf() using FFI from scratch (without jextract)
        // ***********************************************************************
        // 1. Obtain Linker
        Linker linker = Linker.nativeLinker();
        // 2. Obtaining symbol lookup from two places.
        SymbolLookup stdlibLookup = SymbolLookup.loaderLookup()
                .or(linker.defaultLookup());
        // 3. Find function (symbol) printf()
        MemorySegment printfSymbol = stdlibLookup.findOrThrow("printf");
        // 4. Create a function descriptor
        FunctionDescriptor funcDef = FunctionDescriptor.of(
                ValueLayout.JAVA_INT, /* return type (int)  */
                ValueLayout.ADDRESS   /* 1st parameter (const char *format) address in memory */
        );
        // 5. Create a method handle to native function
        MethodHandle printfHandle = linker.downcallHandle(printfSymbol, funcDef);
        try {
            // 6. Invoke the native function
            int charCount = (int) printfHandle.invoke(cString);
            if (charCount < 0) {
                throw new Exception("%s errCode = %d".formatted(printfHandle.toString(), charCount));
            } else if (charCount >= 0) {
                fflush(NULL()); // flush to std out so Java std out will output after wards. If not it will buffer.
                println("Character length = %d".formatted(charCount));
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        // ***********************************************************************
        // * How to call C's printf() using jextract generated classes.
        // ***********************************************************************
        int charCount = printf.makeInvoker().apply(cString);
        logInfo(charCount);

        // *******************************************************************************
        // * How to call printf() with string interpolation such as
        // * printf("Hello %s\n", "Fred");
        // *******************************************************************************
        //    Use the convenient makeInvoker() method to define the variable (variadic) list of arguments (one argument).
        //    Equivalent to:
        //        char *name = "Fred"; // a pointer to a C string (null terminated)
        //        printf("Hello %s\n", name);
        MemorySegment nameCStr = arena.allocateFrom("Fred");
        int charCount1 = printf
                .makeInvoker(stdio_h.C_POINTER) /* 2nd param - C string %s. A pointer to character array address */
                .apply(arena.allocateFrom("Hello %s\n"), /* 1st param - main string to be interpolated */
                        nameCStr                             /* 2nd param - "Fred"                         */
                );

        logInfo(charCount1);

        // *******************************************************************************
        // * How to call printf() with string interpolation such as
        // * printf("%s is %d years old and is %.1f feet tall.\", "Fred", 60, 5.9f);
        // *******************************************************************************
        int charCount2 = printf.makeInvoker(
                stdio_h.C_POINTER,  /* 2nd param - C string %s. A pointer to character array address */
                stdio_h.C_INT,      /* 3rd param - C int %d. Integer value (32bit whole number) */
                stdio_h.C_DOUBLE)   /* 4th param - C double %f. Floating point decimal. floats promote to a double*/
                .apply(arena.allocateFrom("%s is %d years old and is %.1f feet tall.\n"),
                        nameCStr,
                        60,
                        5.9f);

        logInfo(charCount2);
    }
}

/// Logs info using Java's println(). Uses native flush to ensure native code's output is complete. If not Java's
/// output will come first.
///
/// After a native call to printf() the call returns an int value that can mean one of two things.
/// If the value is negative (less than zero) then it's an error code.
/// else (equal or greater than zero) the int value equals the number of characters output to the console.
/// @param charCount error number if less than 0, otherwise the number of characters in the output.
private static void logInfo(int charCount) {
    if (charCount < 0) {
        throw new RuntimeException("printf() error code = %d".formatted(charCount));
    } else if (charCount >= 0) {
        fflush(NULL()); // flush to std out so Java std out will output after wards. If not it will buffer.
        println("Character length = %d".formatted(charCount));
    }
}