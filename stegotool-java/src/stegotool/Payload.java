/**
 *  __  _____  ____  __    ___  _____  ___   ___   _    
 * ( (`  | |  | |_  / /`_ / / \  | |  / / \ / / \ | |   
 * _)_)  |_|  |_|__ \_\_/ \_\_/  |_|  \_\_/ \_\_/ |_|__  
 * ---------------------------------------------------- 
 * 
 * @author dnllns
 * @version v1.0 java, based on Estegomaquina's source (by Daniel Alonso)
 * @since early 2020 
 * @see Source available on https://github.com/Dnllns/stegotool-java 
 * @see Based on Estegomaquina-Android, https://github.com/Dnllns/EstegoMaquina-Android
 *
 */

package stegotool;

import java.util.Base64;
import compression.GZIPCompression;
import encryption.AES;
import stegotool.core.CoreUtils;

/**
 *
 * @author dnllns
 */
public class Payload {

    private byte[] payload;    
    private String fileType;
    
    
    private int rawPayloadSize;
    private int encryptedPayloadSize;
    private int compressedPayloadSize;
    
    
    
    //Constructores
    //--------------------------------------------
    
    /**
     * Constructor string
     * @param data
     */
    public Payload(String data) {
        
    	//Posible fix
    	//payload = (data + " ").getBytes();
    	
    	payload = data.getBytes();
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

    public byte[] getPayload() {
        return payload;
    }
    
    public int getSize() {
    	return this.payload.length;
    }

    
}
