import jdk.incubator.foreign.*;
import java.lang.invoke.*;


public class SayHello {
    public static void main(String[] args) {
       System.out.println(System.getProperty("java.library.path"));
       System.loadLibrary("sayhelloswift");
       var f = CLinker.getInstance().downcallHandle(
          SymbolLookup.loaderLookup().lookup("say_hello").get(),
          MethodType.methodType(void.class),
          FunctionDescriptor.ofVoid()
       );
          
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
         f.invokeExact();  
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
