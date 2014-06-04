import org.apache.commons.codec.binary.Base64;

/**
 * @author ikka
 * @date: 04.06.2014.
 */
public class TestBase64 {
  public static void main(String[] args) {
    String data = "hi,all,how can this happen?";

    byte[] databytes = Base64.encodeBase64(data.getBytes());
    System.out.println(new String(databytes));

//    byte[] encode = java.util.Base64.getEncoder().encode(data.getBytes());
//    System.out.println(new String(encode));

  }
}
