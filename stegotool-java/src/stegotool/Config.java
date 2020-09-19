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

/**
 *
 * @author dnllns
 * @version 1.0 
 */
public class Config {
    
	/**
	 * Configuracion de ficheros de I/O
	 * ---------------------------------
	 */
	//Archivos de entrada y salida de imagenes
    static String inputImgPath;
    static String outputImgPath;
    //Archivos de datos a insertar
    static String inputDataFilePath;
    
    /**
     * Configuracion de opciones de procesado
     * ---------------------------------------
     */
    //Modo de uso de la aplicacion (Insertar/Extraer)
    static String useMode;
    //Indica si hay encriptado
    static boolean isEncrypted;
    static String encryptionAlgorithm;
    static String encryptionPassword;
    //Indica si hay compresion
    static boolean compressed;
    static String compressionAlgorithm;

    /**
     * Configuracion de tipo de archivo de entrada
     * -------------------------------------------
     */
    //No hay archivo, texto plano desde stdin
    static boolean isRawText;
    //Es un archivo
    static boolean isFile;
    //Es un archivo de texto
    static boolean isTxtFile;
    
    /**
     * Configuración de esteganografia
     * -------------------------------
     */
    static String stegoAlgorithm;
    static Pixel startPixel;
    static boolean[] usedRgbChannels;
    
    /**
     * Configuracion de modo de medida de longitud del payload
     */
    //Obtener el payload via marca de inicio/fin 
    static boolean usingStopCode;
    static Byte[] stopCode;
    //Obtener el payload sabiendo su tamaño
    static boolean usingPayloadSize;
    
}
