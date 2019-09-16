package stegoTool;



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
        c.setModo(true);
        c.setEncryption(true);
//        c.setCompression(true);
        c.setInputPath("/home/dnllns/Escritorio/rojo.png");
        c.setPixelInicial(new Pixel(0,0));
        c.setInputMessage("test");
        c.setPassword("password");
        c.setOutputPath("/home/dnllns/Escritorio/rojo2.png");
        
        Core core = new Core(c);
        core.iniciarProcesado();
        
        
//        CoreUtils.printHeader();
//        CoreUtils.parseArgs(args);


        
    }


}
