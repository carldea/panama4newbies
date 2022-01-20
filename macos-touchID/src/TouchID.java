import jdk.incubator.foreign.*;


//import static touchid.touchid_h.c_authenticate_user;

public class TouchID {
    public static void main(String[] args) {
       System.loadLibrary("touchidswift");
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           var  symbolLookup = SymbolLookup.loaderLookup();
           var nativeSymbol = symbolLookup.lookup("authenticate_user").get();
           System.out.println();
           var f = CLinker.systemCLinker()
                   .downcallHandle(nativeSymbol, FunctionDescriptor.ofVoid());

           f.invokeExact();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
