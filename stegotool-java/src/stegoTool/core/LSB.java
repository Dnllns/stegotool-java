
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
        image.actualizarImagenRGB(generarPixeles());
        return image;
    }
    
    /**
     * Extrae de la imagen el la carga insertada
     */
    private String extraerCarga(int binarySize) {

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

                    //Obtener el valor del canal RGB correspondiente
                    //Obtenemos el valor del canal actual
                    int[] valores = new int[3];
                    valores[0] = pixel.getColor().getRed();
                    valores[1] = pixel.getColor().getGreen();
                    valores[2] = pixel.getColor().getBlue();
                    float valorGamaActual = valores[numCanal];

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
    
    
    private String extractHeadder() {

        //obtener la primera linea
        boolean[] cargaBinariaExtraida = new boolean[image.getAncho()];
        int numCanal = 0;
        int contador = 0;

        while (currentPixel.getY() == 0) {

            for (boolean canal : config.getCanalesRGB()) {

                if (contador == image.getAncho()) {
                    break;
                }

                //Control de canal activo
                if (canal) {

                    //Obtener el valor del canal RGB correspondiente
                    int[] valores = new int[3];
                    valores[0] = currentPixel.getColor().getRed();
                    valores[1] = currentPixel.getColor().getGreen();
                    valores[2] = currentPixel.getColor().getBlue();
                    float valorGamaActual = valores[numCanal];

                    // Obtener la carga almacenada en el canal
                    // Si el valor de la gama es par, su carga sera true
                    cargaBinariaExtraida[contador] = (valorGamaActual % 2 == 0);
                    contador++;
                }
                numCanal++;

                if (numCanal == 3) {
                    numCanal = 0;
                }
            }
            nextPixel(config.getStegoAlgorithm());

        }

        //Obtener la fila x = 0 de la imagen y transformarla a caracteres
        String x0CargaExtraida = Payload.binaryDecode(cargaBinariaExtraida);

        //Obtener el tamaño de los headers
        int headerSize = 0;

        try {
            headerSize = Integer.parseInt(x0CargaExtraida.substring(0, x0CargaExtraida.indexOf(";")));
        } catch (Exception x) {

        }

        return extraerCarga(headerSize);

    }


    /**
     * Genera los pixeles modificados que contienen carga
     *
     * @return
     */
    private ArrayList<Pixel> generarPixeles() {

        boolean primerPixel = true;                             //Control primera interaccion
        ArrayList<Pixel> pixelesMod = new ArrayList();          //Almacena los Pixeles modificados

        //Obtener los trozos de Tamaño
        ArrayList<boolean[]> trozosTam = CoreUtils.cortarEnTrozos(CoreUtils.countRGBChannels(channels), binaryData);

        //Obtener los pixeles de Tamaño
        for (boolean[] cargaPixel : trozosTam) {

            //Si no es el primer pixel se actualiza la estructura con el valor del siguiente
            if (!primerPixel) {
                //obtener pixel siguiente
                CoreUtils.nextPixel(image, pixel, stegoAlgorithm);

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
     * Recibe un color, un array booleano que representa N bits, y un array con
     * los canales RGB usados. Devuelve un nuevo color equivalente a el color
     * modificado en funcion del array de bits
     *
     * @param color el color original
     * @param trozoCarga el array con la carga neta del pixel
     * @return el nuevo color
     */
    public Color procesadoRgbDeParidad(Color color, boolean[] trozoCarga) {

        ArrayList<Integer> nuevoColor = new ArrayList();        //Variable para almacenar el nuevo color
        int canalRgbAcual = 0;                                  //Variable de control de la gama RGBA (0=Red, 1=Green, 2=Blue)
        int valorCanal;                                         //Variable que almacena el valor de la gama RGB que se esta usando
        int numBit = 0;                                         //Controla el bit actual de la carga

        //----------------------- PROCESADO DEL COLOR SEGUN EL TROZO --------------
        //----|--
        //----v--
        //Se aplica un redondeo al valor original del canal en función del valor
        //que le toca a ese canal del array de trozoCarga
        for (Boolean canal : channels) {

            //Obtenemos el valor del canal actual
            int[] valores = new int[3];
            valores[0] = color.getRed();
            valores[1] = color.getGreen();
            valores[2] = color.getBlue();

            valorCanal = valores[canalRgbAcual];

            //Control del canal activo
            if (canal) {

                if (trozoCarga[numBit]) {       //PAR

                    if (valorCanal == 255) {

                        //Redondeo hacia abajo
                        if (valorCanal % 2 != 0) {
                            valorCanal--;
                        }
                    } else {

                        //Redondeo hacia arriba
                        if (valorCanal % 2 != 0) {
                            valorCanal++;
                        }
                    }
                } else {                        //IMPAR

                    if (valorCanal % 2 == 0) {
                        valorCanal++;
                    }
                }
                numBit++;
            }

            //Añadir valor de canal modificadp
            nuevoColor.add(valorCanal);
            canalRgbAcual++;   //Se incrementa la variable de canal RGB
        }
        //Devolver nuevo color generado
        return new Color(
                nuevoColor.get(0), //Red
                nuevoColor.get(1), //Green
                nuevoColor.get(2) //Blue
        );
        
        
        
    }

}
