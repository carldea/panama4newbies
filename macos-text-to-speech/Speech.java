
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.*;

import static java.lang.foreign.ValueLayout.ADDRESS;


public class Speech {
    public static void main(String[] args) throws Exception {
       System.loadLibrary("speechswift");

        var  symbolLookup = SymbolLookup.loaderLookup();
        var nativeSymbol = symbolLookup.lookup("say_something").get();

        MethodHandle f;
        f = Linker.nativeLinker()
                .downcallHandle(nativeSymbol, FunctionDescriptor.ofVoid(ADDRESS));
       try (var memorySession= MemorySession.openConfined()) {
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
           var cString = memorySession.allocateUtf8String(say.concat("\n"));
           f.invoke(cString);
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       Thread.sleep(5000);
    }
}
