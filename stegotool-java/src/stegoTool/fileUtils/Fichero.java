package stegoTool.fileUtils;

/**
 *
 * @author dnllns
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Fichero {

    private Path filePath;
    private Boolean existe;

    /**
     * Constructor
     *
     * @param filePath
     */
    public Fichero(String filePath) {

        this.filePath = Paths.get(filePath);
        existe = this.filePath.toFile().exists();

    }

    /**
     * Crea un fichero si no existe
     */
    public void crearFichero() {

        if (!existe()) {
            try {
                Files.createFile(filePath);
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Elimina el fichero
     */
    public void eliminarFichero() {
        try {
            this.filePath.toFile().delete();
        } catch (Exception x) {
        }
    }

    /**
     * Copia el fichero a la nueva ruta
     *
     * @param nuevaRuta
     */
    public void copiar(String nuevaRuta) {
        Fichero nuevo = new Fichero(nuevaRuta);
        if (!nuevo.existe) {
            try {
                Files.copy(
                        this.getFilePath(),
                        nuevo.getFilePath()
                );
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Corta el fichero a la nueva ruta
     *
     * @param nuevaRuta
     */
    public void cortar(String nuevaRuta) {
        Fichero nuevo = new Fichero(nuevaRuta);
        if (!nuevo.existe) {
            this.copiar(nuevaRuta);
            this.eliminarFichero();
            this.filePath = Paths.get(nuevaRuta);
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getRuta() {
        return filePath.toString();
    }

    public File toFile() {
        return filePath.toFile();
    }

    public String getNombre() {
        return this.filePath.getFileName().toString();
    }

    public String getPadre() {
        return this.filePath.getParent().toString();
    }

    public String getExtension() {
        String extension;
        int index = this.filePath.toFile().getName().lastIndexOf(".") + 1;
        extension = this.filePath.toFile().getName().substring(index);
        System.out.println(extension);
        return extension;
    }

    public Boolean existe() {
        return existe;
    }

}
