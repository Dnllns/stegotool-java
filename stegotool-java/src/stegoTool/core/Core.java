package stegoTool.core;

import stegoTool.Config;
import stegoTool.Header;
import stegoTool.ImageEdit;
import stegoTool.Payload;
import stegoTool.Pixel;
import stegoTool.encryption.Md5;

/**
 *
 * @author dnllns
 */
public class Core {

    //Conf
    Config config;

    //Data
    private ImageEdit image;                                   //Controlador de la imagen
    private Payload payload;                                     //Controlador de la carga

    //Config data
    private Pixel currentPixel;
    private Header header;
    private String payloadPassword;

    //Extra
    private String extraction;                               //Resultado de la extraccion

    public Core(Config taskConfig) {

        config = taskConfig;
        image = new ImageEdit(CoreUtils.openImage(config.getInputPath()));
        currentPixel = image.getPixel(
                config.getPixelInicial().getX(),
                config.getPixelInicial().getY()
        );

        if (config.isModo()) {
            payload = new Payload(config.getInputMessage());

            //Encriptado de la carga
            if (config.isEncrypted()) {
                payloadPassword = Md5.getMD5(Math.random() + "");
                payload.encrypt(payloadPassword);
            }

            //Compresion de la carga
            if (config.isCompressed()) {
                payload.compress();
            }

            //Generar los headers
            header = new Header(
                    CoreUtils.binaryEncode(config.getInputMessage()).length(),
                    Md5.getMD5(config.getInputMessage()),
                    payloadPassword,
                    config.getPassword(),
                    config.isCompressed(),
                    config.getStegoAlgorithm()
            );

            payload.encapsulate(header.makeHeader());

        }
    }

    /**
     * Inicia el funcionamiento de la máquina, realiza la funcion que se ha
     * cargado en la configuracion
     *
     * @return
     */
    public void iniciarProcesado() {

        //Comprobar en que modo va a funcionar la maquina
        if (config.isModo()) {
            //Modo encoding

            LSB lsbEncoding = new LSB(
                    config.getCanalesRGB(), payload.binaryEncode(),
                    config.getStegoAlgorithm(), currentPixel, image
            );

            //Insertar carga y guardar la imagen resultante
            lsbEncoding.insert().guardarImagenPNG(config.getOutputPath());

        } else {
            //Modo decoding

            LSB lsbDecoding = new LSB(
                    config.getCanalesRGB(), config.getStegoAlgorithm(), 
                    currentPixel, image
            );
            
            
            
            String rawEncryptedHeader = lsbDecoding.extractHeadder();
            header = new Header(rawEncryptedHeader, config.getPassword());
            String rawPayload = lsbDecoding.extract(header.getBinarySize());
            
      
            Payload payload = new Payload(rawPayload);

            if (header.getEncryptionPassword() != null) {
                payload.decrypt(header.getEncryptionPassword());
            }

            if (header.getCompressed() == 1) {
                payload.decompress();
            }
            
            extraction = payload.getCarga();

        }
        
    }

    
    

    public ImageEdit getImagen() {
        return image;
    }

    public String getExtraccion() {
        return extraction;
    }

}
