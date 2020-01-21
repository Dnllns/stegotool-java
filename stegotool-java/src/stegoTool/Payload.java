package stegoTool;

import stegoTool.core.CoreUtils;
import java.util.Base64;
import stegoTool.compression.GZIPCompression;
import stegoTool.encryption.AES;

/**
 *
 * @author dnllns
 */
public class Payload {

    private String payload;         //payload en texto plano
    

    /**
     * Constructor
     *
     * @param data
     */
    public Payload(String data) {
        payload = data + " ";
    }

    public String binaryEncode() {
        return CoreUtils.binaryEncode(payload);
    }

    public static String binaryDecode(boolean[] bin) {
        return CoreUtils.binaryDecode(bin);
    }

    public void compress() {
        payload = Base64.getEncoder().encodeToString(GZIPCompression.compress(payload));
    }

    public void decompress() {
        payload = GZIPCompression.decompress(Base64.getDecoder().decode(payload));
    }

    public void encrypt(String password) {
        payload = AES.encrypt(payload, password);
    }

    public void decrypt(String password) {
        payload = AES.decrypt(payload, password);
    }

    public void encapsulate(String header) {
        payload = header.length() + ";" + header + payload;
    }

    public String getPayload() {
        return payload;
    }

    
}
