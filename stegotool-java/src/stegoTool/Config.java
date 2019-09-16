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
    private boolean encryption;
    private boolean compression;
    private boolean modo;
    private Pixel pixelInicial;
    private boolean[] canalesRGB;
    private String inputMessage;
    private String inputMessageFilePath;
    private String outputMessageFilePath;
    private String password;

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

    public boolean isEncryption() {
        return encryption;
    }

    public void setEncryption(boolean encryption) {
        this.encryption = encryption;
    }

    public boolean isCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
        this.compression = compression;
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

}
