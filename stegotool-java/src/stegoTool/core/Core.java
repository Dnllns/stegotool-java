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
 */

package stegoTool.core;

import stegoTool.Config;
import stegoTool.Header;
import stegoTool.ImageEdit;
import stegoTool.Payload;
import stegoTool.Pixel;
import stegoTool.encryption.AES;
import stegoTool.encryption.Md5;

/**
 *
 * @author dnllns
 */
public class Core {

    // Conf
    Config config;

    // Data
    private ImageEdit image; // Controlador de la imagen
    private Payload payload; // Controlador de la carga

    // Config data
    private Pixel currentPixel;
    private Header header;
    private String payloadPassword;

    // Extra
    private String extraction; // Resultado de la extraccion

    public Core(Config taskConfig) {

        config = taskConfig;
        image = new ImageEdit(CoreUtils.openImage(config.getInputPath()));
        currentPixel = image.getPixel(config.getPixelInicial().getX(), config.getPixelInicial().getY());

        if (config.isModo()) {
            payload = new Payload(config.getInputMessage());

            // Encriptado de la carga
            if (config.isEncrypted()) {
                payloadPassword = Md5.getMD5(Math.random() + "");
                payload.encrypt(payloadPassword);
            }

            // Compresion de la carga
            if (config.isCompressed()) {
                payload.compress();
            }

            // Generar los headers
            header = new Header(CoreUtils.binaryEncode(config.getInputMessage()).length(),
                    Md5.getMD5(config.getInputMessage()), payloadPassword, config.getPassword(), config.isCompressed(),
                    config.getStegoAlgorithm());

            payload.encapsulate(header.make());

        }
    }

    /**
     * Start encoding process
     */
    private void encode() {

        LSB lsbEncoding = new LSB(config.getCanalesRGB(), payload.binaryEncode(), config.getStegoAlgorithm(),
                currentPixel, image);

        // Insertar carga y guardar la imagen resultante
        lsbEncoding.insert();
        lsbEncoding.getImage().guardarImagenPNG(config.getOutputPath());
    }

    /**
     * Start decoding process
     */
    private void decode() {

        // Configure the LSB object
        LSB lsbDecoding = new LSB(config.getCanalesRGB(), config.getStegoAlgorithm(), currentPixel, image);

        // Get and decode header
        String rawEncryptedHeader = lsbDecoding.extractHeadder();
        header = new Header(rawEncryptedHeader, config.getPassword());

        // Extract payload from image
        String payloadData = lsbDecoding.extract(header.getRawDataBinarySize());

        // Get data
        Payload payload = new Payload(payloadData);
        if (header.getEncryptionPassword() != null) {
            payload.decrypt(header.getEncryptionPassword());
        }
        if (header.getCompressed() == 1) {
            payload.decompress();
        }

        this.extraction = payload.getPayload();
    }

    /**
     * Inicia el funcionamiento de la máquina, realiza la funcion que se ha cargado
     * en la configuracion
     *
     * @return
     */
    public void iniciarProcesado() {

        // Comprobar en que modo va a funcionar la maquina
        if (config.isModo()) {
            // Modo encoding
            encode();

        } else {
            // Modo decoding
            decode();
        }

    }

    // Getters & Setters

    public ImageEdit getImagen() {
        return this.image;
    }

    public String getExtraccion() {
        return this.extraction;
    }

}
