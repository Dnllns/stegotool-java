/**
 *  __  _____  ____  __    ___  _____  ___   ___   _    
 * ( (`  | |  | |_  / /`_ / / \  | |  / / \ / / \ | |   
 * _)_)  |_|  |_|__ \_\_/ \_\_/  |_|  \_\_/ \_\_/ |_|__  
 * ---------------------------------------------------- 
 * 
 * @author dnllns
 * @version v1.1 java, based on Estegomaquina's source (by Daniel Alonso)
 * @since early 2020 
 * @see Source available on https://github.com/Dnllns/stegotool-java 
 * @see Based on Estegomaquina-Android, https://github.com/Dnllns/EstegoMaquina-Android
 *
 */

package compression;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Clase que implementa los metodos necesarios para comprimir y descomprimir 
 * un array de bytes, empleando el algoritmo GZIP 
 * 
 * @author dnllns
 *
 */
public class GZIPCompression {

    public byte[] compress( byte[] inputObject ) {

        if (inputObject == null)  {
            return null;
        }

        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(obj);
            try {
                gzip.write(inputObject);
            } catch (UnsupportedEncodingException ex) {            	
            }
            
            gzip.flush();
            gzip.close();

        } catch (IOException ex) {
        }

        return obj.toByteArray();
    }

    public static String decompress(final byte[] compressed) {
        final StringBuilder outStr = new StringBuilder();
        if ((compressed == null) || (compressed.length == 0)) {
            return "";
        }
        if (isCompressed(compressed)) {
            GZIPInputStream gis = null;
            try {
                gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    outStr.append(line);
                }
            } catch (IOException ex) {
            } finally {
                try {
                    gis.close();
                } catch (IOException ex) {
                }
            }
        } else {
            outStr.append(compressed);
        }
        return outStr.toString();
    }

    public static boolean isCompressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
}
