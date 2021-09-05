import jdk.incubator.foreign.ResourceScope;


import static sayhello.sayhello_h.c_say_hello;

public class SayHello {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));

       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           c_say_hello();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
