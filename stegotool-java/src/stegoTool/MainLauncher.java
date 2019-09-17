package stegoTool;

import stegoTool.core.Core;



/**
 *
 * @author dnllns
 */
public class MainLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        Config c = new Config();
        boolean canales[] = new boolean[3];
        canales[0] = true;
        canales[1] = true;
        canales[2] = true;
        c.setCanalesRGB(canales);
        c.setModo(false);
        c.setEncrypted(true);
        c.setCompressed(false);
        c.setInputPath("C:\\Users\\Casa\\Desktop\\mod.png");
        c.setPixelInicial(new Pixel(0,0));
        c.setInputMessage("test");
        c.setPassword("password");
        c.setOutputPath("C:\\Users\\Casa\\Desktop\\mod.png");
        c.setStegoAlgorithm("linear");
        
        Core core = new Core(c);
        core.iniciarProcesado();
        
        
//        CoreUtils.printHeader();
//        CoreUtils.parseArgs(args);


        
    }


}

        
//        Config c = new Config();
//        boolean canales[] = new boolean[3];
//        canales[0] = true;
//        canales[1] = true;
//        canales[2] = true;
//        c.setCanalesRGB(canales);
//        c.setModo(true);
//        c.setEncrypted(true);
////        c.setCompression(true);
//        c.setInputPath("C:\\Users\\Casa\\Desktop\\nacnic4.jpg");
//        c.setPixelInicial(new Pixel(0,0));
//        c.setInputMessage("test");
//        c.setPassword("password");
//        c.setOutputPath("C:\\Users\\Casa\\Desktop\\mod.png");
//        c.setStegoAlgorithm("linear");
//        
//        Core core = new Core(c);
//        core.iniciarProcesado();
