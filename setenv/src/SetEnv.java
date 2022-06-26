

import org.unix.foo_h;

import java.lang.foreign.Addressable;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;


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
        try (var memorySession = MemorySession.openConfined()) {


            // Create ordinary C strings of JAVA_HOME and PATH
            MemorySegment JAVA_HOME_cstr = memorySession.allocateUtf8String("JAVA_HOME");
            MemorySegment PATH_cstr = memorySession.allocateUtf8String("PATH");

            // Show current values
            // Get JAVA_HOME
            var javaHomeValue = foo_h.getenv(JAVA_HOME_cstr).getUtf8String(0);
            System.out.println("[Before] JAVA_HOME=%s".formatted(javaHomeValue));

            // Get PATH value
            var pathValue = foo_h.getenv(PATH_cstr).getUtf8String(0);
            System.out.println("[Before] PATH=%s".formatted(pathValue));

            // Run java version to see current version
            // Create a C string of command 'java -version'
            MemorySegment java_version_command_cstr = memorySession.allocateUtf8String("java -version");

            // java -version
            System.out.println("Executing java -version");
            foo_h.system(java_version_command_cstr);

            // Overwrite JAVA_HOME
            MemorySegment someJDKPath = memorySession.allocateUtf8String(args[0]);
            foo_h.setenv(JAVA_HOME_cstr, someJDKPath, 1);

            // Get JAVA_HOME
            javaHomeValue = foo_h.getenv(JAVA_HOME_cstr).getUtf8String(0);

            // Build new PATH
            MemorySegment somePath = memorySession.allocateUtf8String("%s/bin:%s".formatted(args[0], pathValue));
            foo_h.setenv(PATH_cstr, somePath, 1);

            pathValue = foo_h.getenv(PATH_cstr).getUtf8String(0);
            System.out.println("[After] JAVA_HOME=%s".formatted(javaHomeValue));
            System.out.println("[After] PATH=%s".formatted(pathValue));
            MemorySegment java_cstr2 = memorySession.allocateUtf8String("java -version");
            System.out.println("java -version");
            foo_h.system(java_cstr2);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
