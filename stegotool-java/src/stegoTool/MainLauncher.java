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

package stegoTool;

import stegoTool.core.Core;

public class MainLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        e();
        d();
        //d();
//        CoreUtils.printHeader();
//        CoreUtils.parseArgs(args);
    }

    public static void e() {

        Config c = new Config();
        boolean canales[] = new boolean[3];
        canales[0] = true;
        canales[1] = true;
        canales[2] = true;
        c.setCanalesRGB(canales);
        c.setModo(true);
        c.setEncrypted(true);
//        c.setCompression(true);
        c.setInputPath("/home/dnllns/Escritorio/rojo.png");
        c.setPixelInicial(new Pixel(0, 0));
        c.setInputMessage("test");
        c.setPassword("password");
        c.setOutputPath("/home/dnllns/Escritorio/mod.png");
        c.setStegoAlgorithm("linear");

        Core core = new Core(c);
        core.iniciarProcesado();

    }

    public static void d() {

        Config c = new Config();
        boolean canales[] = new boolean[3];
        canales[0] = true;
        canales[1] = true;
        canales[2] = true;
        c.setCanalesRGB(canales);
        c.setModo(false);
        c.setEncrypted(true);
        c.setCompressed(false);
        c.setInputPath("/home/dnllns/Escritorio/mod.png");
        c.setPixelInicial(new Pixel(0, 0));
        c.setPassword("password");
        c.setStegoAlgorithm("linear");

        Core core = new Core(c);
        core.iniciarProcesado();
    }

}
