import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.JAVA_BOOLEAN;


//import static touchid.touchid_h.c_authenticate_user;

public class TouchID {
    public static void main(String[] args) {
       System.loadLibrary("touchidswift");
       try (MemorySession memorySession= MemorySession.openConfined()) {
           var  symbolLookup = SymbolLookup.loaderLookup();
           var nativeSymbol = symbolLookup.lookup("authenticate_user").get();
           System.out.println("Identify Yourself!");
           MethodHandle f;
           f = Linker.nativeLinker()
                   .downcallHandle(nativeSymbol, FunctionDescriptor.of(JAVA_BOOLEAN));

           Boolean pass = (boolean) f.invokeExact();
           if (pass) {
               System.out.println("You may enter!");
           } else {
               System.out.println("Access Denied ");
           }
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
