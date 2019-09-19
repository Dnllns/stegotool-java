package stegoTool;

/**
 * @author Daniel Alonso Báscones, as dnllns
 * @version Java 2,0
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageEdit {

    private final BufferedImage imagen;

    /**
     * Constructor
     *
     * @param input
     */
    public ImageEdit(BufferedImage input) {
        this.imagen = input;
    }

    /**
     * Actualiza los pixeles de la imagen correspondientes a los Pixel pasados
     * por parámetro en el array
     *
     * @param pixelesMod
     */
    public void actualizarImagenRGB(ArrayList<Pixel> pixelesMod) {

        //v1 Funcional
        for (Pixel p : pixelesMod) {
            //Modificamos el valor del objeto BufferedImage
            imagen.setRGB(
                    p.getX(),
                    p.getY(),
                    p.getColor().getRGB() //Color en formato INT_ARGB
            );
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Devuelve el pixel pasado por parámetro
     *
     * @param x
     * @param y
     * @return
     */
    public Pixel getPixel(int x, int y) {

        int colorValue = imagen.getRGB(x, y);
        Color c = new Color(colorValue);
        return new Pixel(x, y, c);
    }

    public int getAncho() {
        return imagen.getWidth();
    }

    public int getAlto() {
        return imagen.getHeight();
    }

    /**
     * Guarda la imagen con el nombre pasado por parametro
     *
     * @param rutaGuardado
     */
    public void guardarImagenPNG(String rutaGuardado) {

        File outputfile = new File(rutaGuardado);
        try {
            ImageIO.write(imagen, "png", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(ImageEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
