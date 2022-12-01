import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
public class ApiCredentials {

    private final String apiKey="";
    private final String secretKey= "";
    private String subAccountName ="";

    public ApiCredentials(){}//constructor 

    public String getApiKey(){
        return this.apiKey;
    }//getApiKey

    public String subAccountName(){
        return this.subAccountName;
    }//subAccountName

    /**
     * @param message to sign
     * @return message signed
     */
    public String getSign(String message){
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            return bytesToHex(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message", e);
        }//try-catch
    }//getSign 

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8).toLowerCase();
    }//bytesToHex 
}//ApiCredentials