package stegoTool.compression;

/**
 *
 * @author dnllns
 */
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPCompression {

    public static byte[] compress(final String str) {

        if ((str == null) || (str.length() == 0)) {
            return null;
        }

        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(obj);
            try {
                gzip.write(str.getBytes("UTF-8"));
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