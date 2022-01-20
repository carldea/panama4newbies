import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;


//import static touchid.touchid_h.c_authenticate_user;

public class TouchID {
    public static void main(String[] args) {
       System.loadLibrary("touchidswift");
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           var  symbolLookup = SymbolLookup.loaderLookup();
           var nativeSymbol = symbolLookup.lookup("authenticate_user").get();
           System.out.println("Identify Yourself!");
           MethodHandle f;
           f = CLinker.systemCLinker()
                   .downcallHandle(nativeSymbol, FunctionDescriptor.of(ValueLayout.JAVA_BOOLEAN));

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
