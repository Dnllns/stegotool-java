package stegoTool;

import java.awt.Color;
import java.util.ArrayList;
import stegoTool.encryption.Md5;

/**
 *
 * @author dnllns
 */
public class Core {

    private boolean modo;
    private ImageEdit imagen;                                   //Controlador de la imagen
    private Payload carga;                                     //Controlador de la carga
    private String extraccion;                               //Resultado de la extraccion
    private boolean[] canalesRGB;
    private Pixel pixelActual;
    private int tamCargaBin;
    private String rawEncryptedHeader;
    

    /**
     * Constructor para extraer
     *
     * @param conf
     */
    public Core(Config conf) {

        imagen = new ImageEdit(CoreUtils.openImage(conf.getInputPath()));
        modo = conf.isModo();                                        //Modo extraccion
        pixelActual = conf.getPixelInicial();
        canalesRGB = conf.getCanalesRGB();

        if (modo) {

            carga = new Payload(conf.getInputMessage());

            //Encriptado de la carga
            String encryptionPassword = null;
            if (conf.isEncryption()) {
                //Gen random password for encrypt data
                encryptionPassword = Md5.getMD5(Math.random() + "");
                carga.encrypt(encryptionPassword);
            }

            //Compresion de la carga
            if (conf.isCompression()) {
                carga.compress();
            }
                   
            //Generar los headers
            Header header = new Header(
                    conf.getInputMessage().length(),
                    Md5.getMD5(conf.getInputMessage()),
                    encryptionPassword,
                    conf.getPassword(),
                    conf.isCompression()
            );
            
            rawEncryptedHeader = header.makeHeader();

        }
    }

    /**
     * Inicia el funcionamiento de la máquina, realiza la funcion que se ha
     * cargado en la configuracion
     *
     * @return
     */
    public int iniciarProcesado() {

        //Comprobar en que modo va a funcionar la maquina
        if (modo) {
            //Modo encoding
            insertarCarga();
        } else {
            //Modo decoding
            extraerCarga();
        }
        return 0;
    }

    private void nextPixel() {
        if ((pixelActual).getX() == imagen.getAncho() - 1) {
            pixelActual = imagen.getPixel(0, pixelActual.getY() + 1);
        } else {
            pixelActual = imagen.getPixel(pixelActual.getX() + 1, pixelActual.getY());
        }
    }

    /**
     * Inserta la carga en los pixels correspondientes de la imagen
     */
    private void insertarCarga() {

        //Obtener el numero de canales usados
        int numeroCanalesUsados = CoreUtils.countRGBChannels(canalesRGB);

        //Obtener pixeles actualizados con la Carga_Tamaño
        ArrayList<Pixel> pixelesTamMod = generarPixeles(numeroCanalesUsados, carga.encapsularTam());

        ///Actualizo el pixel actual, cambio de filaY +1
        pixelActual.setX(0);
        pixelActual.setY(pixelActual.getY() + 1);

        //Obtener pixeles actualizados con la Carga_Carga
        ArrayList<Pixel> pixelesCargaMod = generarPixeles(numeroCanalesUsados, carga.getBinary());

        //Modificar los pixeles actualizados en la imagen
        imagen.actualizarImagenRGB(pixelesTamMod);          //TAMAÑO
        imagen.actualizarImagenRGB(pixelesCargaMod);        //CARGA

    }

    /**
     * Recibe un color, un array booleano que representa N bits, y un array con
     * los canales RGB usados. Devuelve un nuevo color equivalente a el color
     * modificado en funcion del array de bits
     *
     * @param color el color original
     * @param trozoCarga el array con la carga neta del pixel
     * @param canalesRGB canales Rgb activos
     * @return el nuevo color
     */
    public Color procesadoRgbDeParidad(Color color, boolean[] trozoCarga, boolean[] canalesRGB) {

        ArrayList<Integer> nuevoColor = new ArrayList();        //Variable para almacenar el nuevo color
        int canalRgbAcual = 0;                                  //Variable de control de la gama RGBA (0=Red, 1=Green, 2=Blue)
        int valorCanal;                                         //Variable que almacena el valor de la gama RGB que se esta usando
        int numBit = 0;                                         //Controla el bit actual de la carga

        //----------------------- PROCESADO DEL COLOR SEGUN EL TROZO --------------
        //----|--
        //----v--
        //Se aplica un redondeo al valor original del canal en función del valor
        //que le toca a ese canal del array de trozoCarga
        for (Boolean canal : canalesRGB) {

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

        //----ʌ--
        //----|--
        //----------------------------------------------------------------FIN
        //Devolver nuevo color generado
        return new Color(
                nuevoColor.get(0), //Red
                nuevoColor.get(1), //Green
                nuevoColor.get(2) //Blue
        );
    }

    /**
     * Genera los pixeles modificados que contienen carga
     *
     * @param numCanales los canales que van a ser usados
     * @param carga la carga
     * @return
     */
    private ArrayList<Pixel> generarPixeles(int numCanales, String carga) {

        boolean primerPixel = true;                             //Control primera interaccion
        ArrayList<Pixel> pixelesMod = new ArrayList();          //Almacena los Pixeles modificados

        //Obtener los trozos de Tamaño
        ArrayList<boolean[]> trozosTam = CoreUtils.cortarEnTrozos(numCanales, carga);

        //Obtener los pixeles de Tamaño
        for (boolean[] cargaPixel : trozosTam) {

            //Si no es el primer pixel se actualiza la estructura con el valor del siguiente
            if (!primerPixel) {
                //obtener pixel siguiente
                nextPixel();

            } else {
                primerPixel = false;
            }

            //Modificacion
            Color nuevoColor = procesadoRgbDeParidad(
                    pixelActual.getColor(), //Color original del Pixel
                    cargaPixel,
                    canalesRGB
            );

            //Actualizar pixel y almacenar
            pixelActual.setColor(nuevoColor);
            pixelesMod.add(pixelActual);
        }
        return pixelesMod;

    }

    /**
     * Extrae de la imagen el la carga insertada
     */
    private void extraerCarga() {

        //INICIALIZAR VARIABLES
        int contadorBitActual = 0;              //Bit actual en la extraccion
        boolean primeraInteraccion = true;      //Control de primera interaccion del bucle
        boolean[] cargaBinariaExtraida;         //Carga binaria extraida
        boolean procesoCompletado = false;      //Control de fin de procesado

        //Obtener el numero de canales usados
        int numeroCanalesUsados = CoreUtils.countRGBChannels(canalesRGB);

        //Obtener el tamaño de la carga         
        obtenerTamCarga();

        //-----------------------EXTRACCION DE LA CARGA --------------
        cargaBinariaExtraida = new boolean[tamCargaBin];

        //Bucle que recorre todos los pixeles implicados
        while (!procesoCompletado) {

            //Si es el primer bit de la interaccion no se actualiza el pixel.
            if (primeraInteraccion) {
                primeraInteraccion = false;
            } else {
                //Si no se ha completado el numero de bits que se deben de extraer de el pixel actual
                //no se actualiza el pixel
                if (contadorBitActual % numeroCanalesUsados == 0) {
                    nextPixel();
                }

            }

            //Recorre los canales activos del color
            //Extrae el valor de la carga que hay en cada canal
            int numCanal = 0;
            for (boolean canal : canalesRGB) {

                //Control de ultimo bit de carga alcanzado
                if (contadorBitActual == tamCargaBin) {
                    procesoCompletado = true;
                    break;
                }

                //Control de canal activo
                if (canal) {

                    //Obtener el valor del canal RGB correspondiente
                    //Obtenemos el valor del canal actual
                    int[] valores = new int[3];
                    valores[0] = pixelActual.getColor().getRed();
                    valores[1] = pixelActual.getColor().getGreen();
                    valores[2] = pixelActual.getColor().getBlue();
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
        extraccion = Payload.binaryDecode(cargaBinariaExtraida);
        extraccion = extraccion.substring(0, extraccion.length() - 1);
    }

    /**
     * Obtiene el tamaño de la carga insertado en la imagen El tamaño se
     * añmacena Esteganograficamente en los primeros pixeles de la imagen y esta
     * delimitado al final por '|#|'
     */
    private void obtenerTamCarga() {

        //obtener la primera linea
        boolean[] cargaBinariaExtraida = new boolean[imagen.getAncho()];
        int numCanal = 0;
        int contador = 0;

        while (pixelActual.getY() == 0) {

            for (boolean canal : canalesRGB) {

                if (contador == imagen.getAncho()) {
                    break;
                }

                //Control de canal activo
                if (canal) {

                    //Obtener el valor del canal RGB correspondiente
                    int[] valores = new int[3];
                    valores[0] = pixelActual.getColor().getRed();
                    valores[1] = pixelActual.getColor().getGreen();
                    valores[2] = pixelActual.getColor().getBlue();
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
            nextPixel();

        }

        //Obtener la fila x = 0 de la imagen y transformarla a caracteres
        String x0CargaExtraida = Payload.binaryDecode(cargaBinariaExtraida);

        //Obtener el tamaño de la carga
        try {
            tamCargaBin = Integer.parseInt(x0CargaExtraida.substring(0, x0CargaExtraida.indexOf("|#|")));
        } catch (Exception x) {

        }

    }

    public ImageEdit getImagen() {
        return imagen;
    }

    public String getExtraccion() {
        return extraccion;
    }

}
