package stegoTool;

import java.awt.Color;

/**
 *
 * @author dnllns
 */
public class Pixel {

    private int x;
    private int y;
    private Color color;

    /**
     * Constructor del objeto Pixel
     *
     * @param x coordenada x
     * @param y coordenada y
     * @param color color del pixel
     */
    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    

    /**
     * ------------------------- SETTERS ---------------------------------------
     * -------------------------------------------------------------------------
     */
    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * ------------------------- GETTERS ---------------------------------------
     * -------------------------------------------------------------------------
     */
    /**
     * @return
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Obtiene la coordenada x del pixel
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada y del pixel
     *
     * @return
     */
    public int getY() {
        return y;
    }
}
