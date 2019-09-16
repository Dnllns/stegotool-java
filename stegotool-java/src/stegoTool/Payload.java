package stegoTool;

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

    /**
     * Recibe un string, devuelbe el mismo string transformado a binario
     *
     * @param payload
     * @return un string equivalente al valor binario del recibido
     */
    public static String binaryEncode(String payload) {
//        byte[] bytes = payload.getBytes();
//        StringBuilder bin = new StringBuilder();
//        for (byte b : bytes) {
//            int val = b;
//            for (int i = 0; i < 8; i++) {
//                bin.append((val & 128) == 0 ? 0 : 1);
//                val <<= 1;
//            }
//        }
//        return bin.toString();

        String finalString = "";
        for (int i = 0; i < payload.length(); i++) {  
            int tempChar = (int) payload.charAt(i);
            finalString = finalString + Integer.toString(tempChar, 2);
        }
        
        return finalString;
    }

    /**
     * @param bin
     * @return
     */
    public static String binaryDecode(boolean[] bin) {
        
        String stringExtraido = "";
        String byyte = "";
        int contadorBit = 0;
        for (boolean bit : bin) {
            if (bit) {
                byyte = byyte + 0;
            } else {
                byyte = byyte + 1;
            }
            contadorBit++;
            if (contadorBit == 8) {
                
                stringExtraido = stringExtraido + (char) Integer.parseInt(byyte, 2);
                byyte = "";
                contadorBit = 0;
                
            }
            
        }
        return stringExtraido;
    }
    
    public void compress() {
        carga = Base64.getEncoder().encodeToString(GZIPCompression.compress(carga));
    }
    
    public void encrypt(String password) {
        carga = AES.encrypt(carga, password);
    }
    
    public String getBinary() {
        return binaryEncode(carga);
    }
    
}
