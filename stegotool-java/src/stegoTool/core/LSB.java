package stegoTool.core;

import java.awt.Color;
import java.util.ArrayList;
import stegoTool.ImageEdit;
import stegoTool.Payload;
import stegoTool.Pixel;

/**
 *
 * @author Casa
 */
public class LSB {

    boolean channels[];
    String binaryData;
    String stegoAlgorithm;
    Pixel pixel;
    ImageEdit image;
    private int binarySize;

    //INSERT CONSTRUCTOR
    public LSB(boolean[] channels, String binaryData, String stegoAlgorithm, Pixel startPixel, ImageEdit image) {
        this.channels = channels;
        this.binaryData = binaryData;
        this.stegoAlgorithm = stegoAlgorithm;
        this.pixel = startPixel;
        this.image = image;
    }

    //EXTRACT CONSTRUCTOR
    public LSB(boolean[] channels, String stegoAlgorithm, Pixel pixel, ImageEdit image) {
        this.channels = channels;
        this.stegoAlgorithm = stegoAlgorithm;
        this.pixel = pixel;
        this.image = image;

    }

    public ImageEdit insert() {
        ArrayList<Pixel> generarPixeles = generarPixeles();
        image.actualizarImagenRGB(generarPixeles);
        return image;
    }

    /**
     * Extrae de la imagen el la carga insertada
     *
     * @param binarySize
     * @return
     */
    public String extract(int binarySize) {

        //INICIALIZAR VARIABLES
        int contadorBitActual = 0;              //Bit actual en la extraccion
        boolean primeraInteraccion = true;      //Control de primera interaccion del bucle
        boolean[] cargaBinariaExtraida;         //Carga binaria extraida
        boolean procesoCompletado = false;      //Control de fin de procesado

        //Obtener el numero de canales usados
        int numeroCanalesUsados = CoreUtils.countRGBChannels(channels);

        //-----------------------EXTRACCION DE LA CARGA --------------
        cargaBinariaExtraida = new boolean[binarySize];

        //Bucle que recorre todos los pixeles implicados
        while (!procesoCompletado) {

            //Si es el primer bit de la interaccion no se actualiza el pixel.
            if (primeraInteraccion) {
                primeraInteraccion = false;
            } else {
                //Si no se ha completado el numero de bits que se deben de extraer de el pixel actual
                //no se actualiza el pixel
                if (contadorBitActual % numeroCanalesUsados == 0) {
                    CoreUtils.nextPixel(image, pixel, stegoAlgorithm);
                }

            }

            //Recorre los canales activos del color
            //Extrae el valor de la carga que hay en cada canal
            int numCanal = 0;
            for (boolean canal : channels) {

                //Control de ultimo bit de carga alcanzado
                if (contadorBitActual == binarySize) {
                    procesoCompletado = true;
                    break;
                }

                //Control de canal activo
                if (canal) {

                    float valorGamaActual = pixel.getRGB()[numCanal];

                    //Obtener carga
                    //Si el valor de la gama es par, su carga sera true
                    cargaBinariaExtraida[contadorBitActual] = valorGamaActual % 2 == 0;
                    contadorBitActual++;
                }

                numCanal++;
            }
        }

        //Transformar de binario a caracteres
        String aux = Payload.binaryDecode(cargaBinariaExtraida);
        return aux.substring(0, aux.length() - 1);
    }

    public String extractHeadder() {

        //obtener la primera linea
        String rawByte = "";
        String textBuffer = "";

        int chCount = 0;
        int bitCount = 0;
        boolean done = false;

        while (!done) {

            if (channels[chCount]) {
                //Obtener el valor del canal RGB correspondiente            
                int currentChVal = pixel.getRGB()[chCount];

                // Obtener la carga almacenada en el canal
                // Si el valor de la gama es par, su carga sera true
                if (currentChVal % 2 == 0) {
                    rawByte += "0";
                } else {
                    rawByte += "1";
                }

                bitCount++;
                if (bitCount == 8) {
                    bitCount = 0;

                    //CoreUtils.binaryDecode(ochoBits);
                    String c = new Character((char) Integer.parseInt(rawByte, 2)).toString();
                    rawByte = "";

                    if (c.equals(";")) {
                        done = true;
                        break;
                    } else {
                        textBuffer += c;   //Añadir el caracter al buffer
                    }
                }
            }

            chCount++;
            if (chCount == 3) {
                chCount = 0;
                pixel = CoreUtils.nextPixel(image, pixel, stegoAlgorithm);

            }
        }

        //Tamaño del header
        int headerSize = Integer.parseInt(textBuffer);

        return extract(headerSize);

    }

    public char lsbChar() {

        //obtener la primera linea
        String rawByte = "";
        String textBuffer = "";

        int chCount = 0;
        int bitCount = 0;
        boolean done = false;

        while (bitCount < 8) {
            //Obtener el valor del canal RGB correspondiente            
            int currentChVal = pixel.getRGB()[chCount];

            if (channels[chCount]) {

                // Obtener la carga almacenada en el canal
                // Si el valor de la gama es par, su carga sera true
                if (currentChVal % 2 == 0) {
                    rawByte += "0";
                } else {
                    rawByte += "1";
                }

            }
            chCount++;
            if (chCount == 3) {
                chCount = 0;
                pixel = CoreUtils.nextPixel(image, pixel, stegoAlgorithm);

            }

        }

        if (channels[chCount]) {
            //Obtener el valor del canal RGB correspondiente            
            int currentChVal = pixel.getRGB()[chCount];

            // Obtener la carga almacenada en el canal
            // Si el valor de la gama es par, su carga sera true
            if (currentChVal % 2 == 0) {
                rawByte += "0";
            } else {
                rawByte += "1";
            }

            bitCount++;
            if (bitCount == 8) {
                bitCount = 0;

                //CoreUtils.binaryDecode(ochoBits);
                int charCode = Integer.parseInt(rawByte, 2);
                String c = new Character((char) charCode).toString();
                rawByte = "";

                textBuffer += c;   //Añadir el caracter al buffer

            }
        }

        chCount++;
        if (chCount == 3) {
            chCount = 0;
            pixel = CoreUtils.nextPixel(image, pixel, stegoAlgorithm);

        }

    }

    /**
     * Genera los pixeles modificados que contienen carga
     *
     * @return
     */
    private ArrayList<Pixel> generarPixeles() {

        boolean primerPixel = true;                             //Control primera interaccion
        ArrayList<Pixel> pixelesMod = new ArrayList();          //Almacena los Pixeles modificados

        //Obtener los trozos 
        ArrayList<String> splitedBinary = CoreUtils.cortarEnTrozos(
                CoreUtils.countRGBChannels(channels),
                binaryData
        );

        //Generar un pixel para cada trozo
        for (String cargaPixel : splitedBinary) {

            //Si no es el primer pixel se actualiza la estructura con el valor del siguiente
            if (!primerPixel) {
                //obtener pixel siguiente
                pixel = CoreUtils.nextPixel(image, pixel, stegoAlgorithm);

            } else {
                primerPixel = false;
            }

            //Modificacion
            Color nuevoColor = procesadoRgbDeParidad(
                    pixel.getColor(), //Color original del Pixel
                    cargaPixel
            );

            //Actualizar pixel y almacenar
            pixel.setColor(nuevoColor);
            pixelesMod.add(pixel);
        }
        return pixelesMod;

    }

    /**
     *
     * IMPORTANTE trozo carga debe de tener el mismotamaño que el numero de
     * canales activos
     *
     * Recibe un color, un array booleano que representa N bits, y un array con
     * los canales RGB usados. Devuelve un nuevo color equivalente a el color
     * modificado en funcion del array de bits
     *
     * @param color el color original
     * @param trozoCarga el array con la carga neta del pixel
     * @return el nuevo color
     */
    public Color procesadoRgbDeParidad(Color color, String trozoCarga) {

        int[] nuevoColor = new int[3];       //Variable para almacenar el nuevo color
        int valorCanal;                                         //Variable que almacena el valor de la gama RGB que se esta usando

        //----------------------- PROCESADO DEL COLOR SEGUN EL TROZO --------------
        //----|--
        //----v--
        //Se aplica un redondeo al valor original del canal en función del valor
        //que le toca a ese canal del array de trozoCarga
        for (int i = 0; i < CoreUtils.countRGBChannels(channels); i++) {

            //Obtenemos el valor del canal actual
            valorCanal = Pixel.getRGB(color)[i];

            //Control del canal activo
            if (channels[i]) {

                //Modificacion del valor del canal
                //Se aplica un redondeo al valor para insertar la carga
                if (trozoCarga.charAt(i) == '0') {       //PAR
                    if (valorCanal == 255) {
                        //Redondeo hacia abajo
                        if (valorCanal % 2 != 0) {
                            valorCanal--;
                        }
                    } else {     //Redondeo hacia arriba
                        if (valorCanal % 2 != 0) {
                            valorCanal++;
                        }
                    }
                } else {                        //IMPAR
                    if (valorCanal % 2 == 0) {
                        valorCanal++;
                    }
                }
            }

            //Añadir valor del canal
            nuevoColor[i] = valorCanal;
        }
        //Devolver nuevo color generado
        return new Color(
                nuevoColor[0], //Red
                nuevoColor[1], //Green
                nuevoColor[2] //Blue
        );

    }

    public int getBinarySize() {
        return binarySize;
    }

}
