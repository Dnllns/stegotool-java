package stegoTool;

/**
 *
 * @author dnllns
 */
public class Payload {

    private final String carga;               //Carga en texto plano
    private final String binary;        //Carga binaria

    /**
     * Constructor
     *
     * @param contenidoOriginal
     */
    public Payload(String contenidoOriginal) {
        carga = contenidoOriginal + " ";
        binary = binaryEncode(carga);
    }

    /**
     * Recibe un string, devuelbe el mismo string transformado a binario
     *
     * @param payload
     * @return un string equivalente al valor binario del recibido
     */
    public static String binaryEncode(String payload) {
        byte[] bytes = payload.getBytes();
        StringBuilder bin = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                bin.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return bin.toString();
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

    /**
     * Genera la carga que contiene la info del tamaño
     *
     * @return
     */
    public String encapsularTam() {
        return binaryEncode(binary.length() + "|#||");
    }

    public String getBinary() {
        return binary;
    }

}
