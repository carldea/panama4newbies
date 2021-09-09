import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.ResourceScope;


import static speech.speech_h.c_say_something;

public class Speech {
    public static void main(String[] args) throws Exception {
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
           c_say_something(CLinker.toCString(say, scope));
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
       Thread.sleep(5000);
    }
}
