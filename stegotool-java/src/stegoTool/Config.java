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
 */

package stegoTool;

/**
 *
 * @author dnllns
 * @version 1.0 
 */
public class Config {

    //Files
    private String inputPath;
    private String outputPath;
    private String inputMessageFilePath;
    private String outputMessageFilePath;

    //Options
    private boolean modo;
    private boolean encrypted;
    private boolean compressed;
    
    //Data
    private String inputMessage;
    private Pixel pixelInicial;
    private boolean[] canalesRGB;
    private String password;
    private String stegoAlgorithm;

    // region  Getter & Setter

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encryption) {
        this.encrypted = encryption;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compression) {
        this.compressed = compression;
    }

    public boolean isModo() {
        return modo;
    }

    public void setModo(boolean modo) {
        this.modo = modo;
    }

    public Pixel getPixelInicial() {
        return pixelInicial;
    }

    public void setPixelInicial(Pixel pixelInicial) {
        this.pixelInicial = pixelInicial;
    }

    public boolean[] getCanalesRGB() {
        return canalesRGB;
    }

    public void setCanalesRGB(boolean[] canalesRGB) {
        this.canalesRGB = canalesRGB;
    }

    public String getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
    }

    public String getInputMessageFilePath() {
        return inputMessageFilePath;
    }

    public void setInputMessageFilePath(String inputMessageFilePath) {
        this.inputMessageFilePath = inputMessageFilePath;
    }

    public String getOutputMessageFilePath() {
        return outputMessageFilePath;
    }

    public void setOutputMessageFilePath(String outputMessageFilePath) {
        this.outputMessageFilePath = outputMessageFilePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStegoAlgorithm() {
        return stegoAlgorithm;
    }

    public void setStegoAlgorithm(String stegoAlgorithm) {
        this.stegoAlgorithm = stegoAlgorithm;
    }

    // endregion
    
    
}
