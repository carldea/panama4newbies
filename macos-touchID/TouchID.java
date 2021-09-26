import jdk.incubator.foreign.*;
import java.lang.invoke.*;

//import static touchid.touchid_h.c_authenticate_user;

public class SayHello {
    public static void main(String[] args) {
       System.loadLibrary("touchidswift");
       var f = CLinker.getInstance().downcallHandle(
          SymbolLookup.loaderLookup().lookup("authenticate_user").get(),
          MethodType.methodType(void.class),
          FunctionDescriptor.ofVoid()
       );
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           //c_authenticate_user();
           f.invokeExact();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
