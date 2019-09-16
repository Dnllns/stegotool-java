/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stegoTool;

/**
 *
 * @author dnllns
 */
public class Config {
    
    private String inputPath;
    private String outputPath;
    private String encryption;
    private boolean modo;
    private Pixel pixelInicial;
    private boolean[] canalesRGB;
    private String inputMessage;
    private String inputMessageFilePath;
    private String outputMessageFilePath;
    private String password;

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @param outputPath the outputPath to set
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return the encryption
     */
    public String getEncryption() {
        return encryption;
    }
    

    public boolean getModo() {
        return isModo();
    }

    /**
     * @param encryption the encryption to set
     */
    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    /**
     * @return the modo
     */
    public boolean isModo() {
        return modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(boolean modo) {
        this.modo = modo;
    }

    /**
     * @return the pixelInicial
     */
    public Pixel getPixelInicial() {
        return pixelInicial;
    }

    /**
     * @param pixelInicial the pixelInicial to set
     */
    public void setPixelInicial(Pixel pixelInicial) {
        this.pixelInicial = pixelInicial;
    }

    /**
     * @return the canalesRGB
     */
    public boolean[] getCanalesRGB() {
        return canalesRGB;
    }

    public Config() {
    }



    /**
     * @return the inputMessage
     */
    public String getInputMessage() {
        return inputMessage;
    }

    /**
     * @param inputMessage the inputMessage to set
     */
    public void setInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
    }

 /**
     * @param canalesRGB the canalesRGB to set
     */
    public void setCanalesRGB(boolean[] canalesRGB) {
        this.canalesRGB = canalesRGB;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * @return the inputMessageFilePath
     */
    public String getInputMessageFilePath() {
        return inputMessageFilePath;
    }

    /**
     * @param inputMessageFilePath the inputMessageFilePath to set
     */
    public void setInputMessageFilePath(String inputMessageFilePath) {
        this.inputMessageFilePath = inputMessageFilePath;
    }

   

    /**
     * @return the outputMessageFilePath
     */
    public String getOutputMessageFilePath() {
        return outputMessageFilePath;
    }

    /**
     * @param outputMessageFilePath the outputMessageFilePath to set
     */
    public void setOutputMessageFilePath(String outputMessageFilePath) {
        this.outputMessageFilePath = outputMessageFilePath;
    }
    
    
}
