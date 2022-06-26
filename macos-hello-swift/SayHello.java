
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
public class SayHello {
    public static void main(String[] args) {
       System.out.println(System.getProperty("java.library.path"));
       System.loadLibrary("sayhelloswift");
        try (var memorySession = MemorySession.openConfined()) {

           MemorySegment symbol = SymbolLookup.loaderLookup().lookup("say_hello")
            .or(() -> Linker.nativeLinker().defaultLookup().lookup("say_hello"))
            .orElseThrow(() -> new RuntimeException("cant find symbol"));

            var mh = Linker.nativeLinker().downcallHandle(
              symbol,
              FunctionDescriptor.ofVoid()
            );


            mh.invokeExact();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
