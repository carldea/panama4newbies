import jdk.incubator.foreign.*;
import java.lang.invoke.*;

import static jdk.incubator.foreign.SegmentAllocator.implicitAllocator;


public class Speech {
    public static void main(String[] args) throws Exception {
       System.loadLibrary("speechswift");

        var  symbolLookup = SymbolLookup.loaderLookup();
        var nativeSymbol = symbolLookup.lookup("say_something").get();

        MethodHandle f;
        f = CLinker.systemCLinker()
                .downcallHandle(nativeSymbol, FunctionDescriptor.ofVoid(ValueLayout.OfAddress.ADDRESS));
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           String say = null;
           if (args == null || args.length == 0) {
               say = "Hello there";
           } else {
               if (args[0].trim().equals("")) {
                   say = "Hello there";
               } else {
                   say = args[0];
               }
           }
           System.out.println("Saying: " + say);
           Addressable cString = implicitAllocator().allocateUtf8String(say.concat("\n"));
           f.invokeExact(cString);
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       Thread.sleep(5000);
    }
}
