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

    private String carga;         //Carga en texto plano
    

    /**
     * Constructor
     *
     * @param contenidoOriginal
     */
    public Payload(String contenidoOriginal) {
        carga = contenidoOriginal + " ";
    }

    public String binaryEncode() {
        return CoreUtils.binaryEncode(carga);
    }

    public static String binaryDecode(boolean[] bin) {
        return CoreUtils.binaryDecode(bin);
    }

    public void compress() {
        carga = Base64.getEncoder().encodeToString(GZIPCompression.compress(carga));
    }

    public void decompress() {
        carga = GZIPCompression.decompress(Base64.getDecoder().decode(carga));
    }

    public void encrypt(String password) {
        carga = AES.encrypt(carga, password);
    }

    public void decrypt(String password) {
        carga = AES.decrypt(carga, password);
    }

    public void encapsulate(String header) {
        carga = header.length() + ";" + header + carga;
    }

    public String getCarga() {
        return carga;
    }

    
}
