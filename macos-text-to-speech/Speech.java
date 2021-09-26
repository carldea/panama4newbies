import jdk.incubator.foreign.*;
import java.lang.invoke.*;


public class Speech {
    public static void main(String[] args) throws Exception {
       System.loadLibrary("speechswift");
       var f = CLinker.getInstance().downcallHandle(
          SymbolLookup.loaderLookup().lookup("say_something").get(),
          MethodType.methodType(void.class, MemoryAddress.class),
          FunctionDescriptor.ofVoid(CLinker.C_POINTER)
       );       
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
           f.invokeExact(CLinker.toCString(say, scope).address());
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       Thread.sleep(5000);
    }
}
