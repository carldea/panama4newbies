
import jdk.incubator.foreign.*;
import org.unix.foo_h;

import static jdk.incubator.foreign.CLinker.*;

/**
 * setenv() and getenv() .
 * @author cdea
 */
public class SetEnv {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Must specify a new JAVA_HOME jdk path directory. ");
            return;
        }
        try (var scope = ResourceScope.newConfinedScope()) {
            var allocator = SegmentAllocator.ofScope(scope);

            // Create ordinary C strings of JAVA_HOME and PATH
            MemorySegment JAVA_HOME_cstr = CLinker.toCString("JAVA_HOME", allocator);
            MemorySegment PATH_cstr = CLinker.toCString("PATH", allocator);

            // Show current values
            // Get JAVA_HOME
            var javaHomeValue = toJavaString(foo_h.getenv(JAVA_HOME_cstr));
            System.out.println("[Before] JAVA_HOME=%s".formatted(javaHomeValue));

            // Get PATH value
            var pathValue = toJavaString(foo_h.getenv(PATH_cstr));
            System.out.println("[Before] PATH=%s".formatted(pathValue));

            // Run java version to see current version
            // Create a C string of command 'java -version'
            MemorySegment java_version_command_cstr = CLinker.toCString("java -version", allocator);

            // java -version
            System.out.println("Executing java -version");
            foo_h.system(java_version_command_cstr);

            // Overwrite JAVA_HOME
            MemorySegment someJDKPath = CLinker.toCString(args[0], allocator);
            foo_h.setenv(JAVA_HOME_cstr, someJDKPath, 1);

            // Get JAVA_HOME
            javaHomeValue = toJavaString(foo_h.getenv(JAVA_HOME_cstr));

            // Build new PATH
            MemorySegment somePath = CLinker.toCString("%s/bin:%s".formatted(args[0], pathValue), allocator);
            foo_h.setenv(PATH_cstr, somePath, 1);

            pathValue = toJavaString(foo_h.getenv(PATH_cstr));
            System.out.println("[After] JAVA_HOME=%s".formatted(javaHomeValue));
            System.out.println("[After] PATH=%s".formatted(pathValue));
            MemorySegment java_cstr2 = CLinker.toCString("java -version", allocator);
            System.out.println("java -version");
            foo_h.system(java_cstr2);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
