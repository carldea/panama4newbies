import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.ResourceScope;

public class CardReader {
    public static void main(String[] args){
       try (ResourceScope scope= ResourceScope.newConfinedScope()) {
           cardreader.cardreader_h.readCard();
       } catch (Throwable throwable) {
           throwable.printStackTrace();
       }
    }
}
