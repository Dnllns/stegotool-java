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

package stegoTool.fileUtils;

/**
 *
 * @author dnllns
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FicheroTxt extends Fichero {

    private ArrayList<String> contenido;

    public FicheroTxt(String rutaArchivo) {
        super(rutaArchivo);
    }

    /**
     * Abre el archivo, lee su contenido y lo carga en la estructura
     */
    public void leerFichero() {
        if (this.existe()) {
            BufferedReader bufferedReader;
            Stream<String> lineasTxt;
            try {
                bufferedReader = Files.newBufferedReader(this.getFilePath());
                lineasTxt = bufferedReader.lines();
                contenido = new ArrayList<>();
                lineasTxt.forEach(linea -> {
                    contenido.add(linea);
                });
                bufferedReader.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Escribe el string pasado por parametro en una nueva linea del archivo
     *
     * @param contenido
     */
    public void escribirInformacion(String contenido) {
        if (this.existe()) {
            try {

                BufferedWriter bufferedWriter = Files.newBufferedWriter(this.getFilePath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                bufferedWriter.write(contenido);
                bufferedWriter.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Escribe el ArrayList String pasado por parametro en una nueva linea del
     * archivo
     *
     * @param contenidoDocumento
     */
    public void escribirInformacion(ArrayList<String> contenidoDocumento) {
        if (this.existe()) {
            try {

                BufferedWriter bufferedWriter = Files.newBufferedWriter(this.getFilePath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                for (String linea : contenidoDocumento) {
                    bufferedWriter.write("\n" + linea);
                }
                bufferedWriter.close();
            } catch (IOException ex) {
            }

        }
    }

    public void sobreescribirInformacion(ArrayList<String> contenidoDocumento) {
        if (this.existe()) {
            try {

                BufferedWriter bufferedWriter = Files.newBufferedWriter(this.getFilePath(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                for (String linea : contenidoDocumento) {
                    bufferedWriter.write("\n" + linea);
                }
                bufferedWriter.close();
            } catch (IOException ex) {
            }

        }
    }

}
