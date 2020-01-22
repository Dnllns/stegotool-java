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
     * @param data
     */
    public Payload(String data) {
        payload = data + " ";
    }

    /**
     * Encode payload into a binary string
     * @return binary payload 
     */
    public String binaryEncode() {
        return CoreUtils.binaryEncode(payload);
    }

    /**
     * Decode a binary payload string
     * @param bin, the binary payload
     * @return raw payload
     */
    public static String binaryDecode(boolean[] bin) {
        return CoreUtils.binaryDecode(bin);
    }

    /**
     * Compress payload using GZIP algorithm
     */
    public void compress() {
        payload = Base64.getEncoder().encodeToString(GZIPCompression.compress(payload));
    }

    /**
     * Decompress payload
     */
    public void decompress() {
        payload = GZIPCompression.decompress(Base64.getDecoder().decode(payload));
    }

    /**
     * Encrypt payload with password using AES algorithm
     * @param password, the password for encrypt the payload
     */
    public void encrypt(String password) {
        payload = AES.encrypt(payload, password);
    }

    /**
     * Decrypt AES encrypted payload
     * @param password, the password used in encryption
     */
    public void decrypt(String password) {
        payload = AES.decrypt(payload, password);
    }

    /**
     * Encapsulate the header
     * @param header
     */
    public void encapsulate(String header) {
        payload = header.length() + ";" + header + payload;
    }

    public String getPayload() {
        return payload;
    }

    
}
