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

import java.awt.Color;

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

    public int[] getRGB() {

        int[] valores = new int[3];
        valores[0] = color.getRed();
        valores[1] = color.getGreen();
        valores[2] = color.getBlue();
        
        return valores;

    }
    
    public static int[] getRGB(Color color) {

        int[] valores = new int[3];
        valores[0] = color.getRed();
        valores[1] = color.getGreen();
        valores[2] = color.getBlue();
        
        return valores;

    }
}
