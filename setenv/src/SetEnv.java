
import jdk.incubator.foreign.*;
import org.unix.foo_h;

import static jdk.incubator.foreign.CLinker.*;

/**
 * setenv() and getenv() .
 * @author cdea
 */
public class SetEnv {
    public static void main(String[] args) {

        try (var scope = ResourceScope.newConfinedScope()) {
            var allocator = SegmentAllocator.ofScope(scope);
            MemorySegment JAVA_HOME_cstr = CLinker.toCString("JAVA_HOME", allocator);
            MemorySegment PATH_cstr = CLinker.toCString("PATH", allocator);
            // Using a MethodHandle
            var javaHomeValue = toJavaString(foo_h.getenv(JAVA_HOME_cstr));
            System.out.println("[Before] JAVA_HOME=%s".formatted(javaHomeValue));
            var pathValue = toJavaString(foo_h.getenv(PATH_cstr));
            System.out.println("[Before] PATH=%s".formatted(pathValue));

            MemorySegment java_cstr = CLinker.toCString("java -version".formatted(pathValue), allocator);

            // java -version
            System.out.println("export PATH=%s ; java -version");
            foo_h.system(java_cstr);

            MemorySegment someJDKPath = CLinker.toCString("/User/cdea/sdks/zulu16.0.89-ea-jdk16.0.0-ea.36-macosx_x64", allocator);
            foo_h.setenv(JAVA_HOME_cstr, someJDKPath, 1);
            javaHomeValue = toJavaString(foo_h.getenv(JAVA_HOME_cstr));

            MemorySegment somePath = CLinker.toCString(javaHomeValue +"/bin:" + pathValue, allocator);
            foo_h.setenv(PATH_cstr, somePath, 1);

            pathValue = toJavaString(foo_h.getenv(PATH_cstr));
            System.out.println("[After] JAVA_HOME=%s".formatted(javaHomeValue));
            System.out.println("[After] PATH=%s".formatted(pathValue));
            MemorySegment java_cstr2 = CLinker.toCString("export PATH=%s ; java -version".formatted(pathValue), allocator);
            System.out.println("PATH=%s ; java -version".formatted(pathValue));
            foo_h.system(java_cstr2);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
