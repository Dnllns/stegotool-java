package stegoTool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author dnllns
 */
public class CoreUtils {

    public static int countRGBChannels(boolean[] canalesRGB) {

        int numeroCanalesUsados = 0;
        for (boolean canal : canalesRGB) {
            if (canal) {
                numeroCanalesUsados++;
            }
        }
        return numeroCanalesUsados;
    }

    public ArrayList<boolean[]> cortarEnTrozos_v2(int tamTrozo, String string) {

        ArrayList<boolean[]> cargaTroceada = new ArrayList();       //Contiene los arrays agrupados 
        String[] trozos = string.split("(?<=\\G.{4})");
        return null;

    }

    /**
     * Obtiene la carga y la va agrupando en arrays de N en N para facilitar su
     * manipulacion
     *
     * @param numero el numero de unidades por array
     * @param carga la carga binaria
     * @return
     */
    public static ArrayList<boolean[]> cortarEnTrozos(int numero, String carga) {

        boolean[] bits = new boolean[numero];                       //Array que contiene (n) bits
        ArrayList<boolean[]> cargaTroceada = new ArrayList();       //Contiene los arrays agrupados

        int currentChar = 0;                                        //Posicion del caracter actual del bucle
        int contadorBit = 0;                                        //Controlar la posicion del array de bits

        //Bucle que recorre la carga
        while (currentChar < carga.length()) {

            //Si el caracter = 0 --> True
            //Si el caracter = 1 --> False
            bits[contadorBit] = Integer.parseInt(String.valueOf(carga.charAt(currentChar))) == 0;

            //Incrementar contadores
            currentChar++;
            contadorBit++;

            //Controlar numero de elementos por array
            if (contadorBit == numero) {
                cargaTroceada.add(bits);
                bits = new boolean[numero];
                contadorBit = 0;
            }
        }

        return cargaTroceada;

    }

    static void printHeader() {

        String title = "\n"
                + " +-----------------------------------------------------------------+\n\n"
                + "    ▄▄▄▄▄      ▄▄▄▄▀ ▄███▄     ▄▀  ████▄    ▄▄▄▄▀ ████▄ ████▄ █     \n"
                + "   █     ▀▄ ▀▀▀ █    █▀   ▀  ▄▀    █   █ ▀▀▀ █    █   █ █   █ █     \n"
                + " ▄  ▀▀▀▀▄       █    ██▄▄    █ ▀▄  █   █     █    █   █ █   █ █     \n"
                + "  ▀▄▄▄▄▀       █     █▄   ▄▀ █   █ ▀████    █     ▀████ ▀████ ███▄  \n"
                + "              ▀      ▀███▀    ███          ▀                      ▀ \n"
                + "                                                          v1.2 Beta \n"
                + " +-- github.com/dnllns/stegotool-java -----------------------------+\n";

        System.out.println(title);

    }

    public static void parseArgs(String args[]) {

        //Help menu
        if (args[0].equals("-h")) {
            String data = "StegoTool java v1.2 by Dnllns\n"
                    + "\nUsage: "
                    + "\nEncode -e inputFile outputFile.png text_data [-p password] "
                    + "\n       -e inputFile outputFile.png -f file_data [-p password]"
                    + "\nDecode -d inputFile [-p password] [-f output_datafile]\n";

            System.out.println(data);

        }

        //Encoding mode
        if (args[0].equals("-e")) {

            Config c = new Config();
            c.setModo(true);                        //modo encoding
            c.setInputPath(args[1]);                //input path
            c.setOutputPath(args[2]);               //output path

            
            if (args[3].equals("-f")) {             //input message inside file
                c.setInputMessageFilePath(args[4]); //input message file path

                if (args[5].equals("-p")) {         //encryption mode
                    c.setPassword(args[6]);         //header password
                }

            } else {
                c.setInputMessage(args[3]);
                if (args[4].equals("-p")) {
                    c.setPassword(args[5]);
                }
            }
            enc(c);

        }
        if (args[0].equals("-d")) {

            Config c = new Config();
            c.setInputPath(args[1]);
            if (args[2].equals("-p")) {
                c.setPassword(args[3]);
                
                if (args[4].equals("-f")) {
                    c.setOutputMessageFilePath(args[5]);
                }
                
            } else {

                if (args[2].equals("-f")) {
                    c.setOutputMessageFilePath(args[3]);
                }

            }

            c.setPassword(args[2]);
            if (args[3].equals("-f")) {
                //output result in file
                System.out.print("Implementado en versiones futuras");
            }
            dec(c);
        }

    }

    public static BufferedImage openImage(String in) {

        BufferedImage i = null;
        try {
            i = ImageIO.read(new File(in));
        } catch (IOException ex) {
            Logger.getLogger(MainLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;

    }

    public static void dec(Config conf) {

        BufferedImage i = openImage(conf.getInputPath());
        Core c = new Core(conf);
        c.iniciarProcesado();
        System.out.println(c.getExtraccion());
    }

    public static void enc(Config conf) {

        BufferedImage i = openImage(conf.getInputPath());
        Core c = new Core(conf);
        c.iniciarProcesado();
        c.getImagen().guardarImagenPNG(conf.getOutputPath());

    }
}