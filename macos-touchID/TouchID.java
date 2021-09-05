import jdk.incubator.foreign.ResourceScope;


import static touchid.touchid_h.c_authenticate_user;

public class SayHello {
    public static void main(String[] args) {
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           c_authenticate_user();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
