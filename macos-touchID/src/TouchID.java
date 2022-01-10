import jdk.incubator.foreign.*;


//import static touchid.touchid_h.c_authenticate_user;

public class TouchID {
    public static void main(String[] args) {
       System.loadLibrary("touchidswift");
       var cLinker = CLinker.systemCLinker();
        System.out.println(cLinker.lookup("authenticate_user").get());
       var f = cLinker.downcallHandle(
          cLinker.lookup("authenticate_user").get(),
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
