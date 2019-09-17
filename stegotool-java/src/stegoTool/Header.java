/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stegoTool;

import stegoTool.encryption.AES;

public class Header {

    private int binarySize;
    private String rawDataMD5;
    private String encryptionPassword;
    private String headerPassword;
    private int compressed;
    private String stegoAlgorithm;

    public Header(String rawEncryptedHeader, String password) {

        String decrypted = AES.decrypt(rawEncryptedHeader, password);
        String[] parts = decrypted.split(";");
        encryptionPassword = parts[0];
        rawDataMD5 = parts[1];
        binarySize = Integer.parseInt(parts[2]);
        compressed = Integer.parseInt(parts[3]);
        stegoAlgorithm = parts[4];
    }

    public Header(
            int binarySize,
            String rawDataMD5,
            String encryptionPassword,
            String headerPassword,
            boolean compressed,
            String stegoAlgorithm
    ) {
        this.binarySize = binarySize;
        this.rawDataMD5 = rawDataMD5;
        this.encryptionPassword = encryptionPassword;
        this.headerPassword = headerPassword;
        this.stegoAlgorithm = stegoAlgorithm;
        if (compressed) {
            this.compressed = 1;
        } else {
            this.compressed = 0;
        }

    }

    public String makeHeader() {

        String rawHeader = encryptionPassword + ";"
                + rawDataMD5 + ";"
                + binarySize + ";"
                + compressed + ";"
                + stegoAlgorithm;

        //Encrypted header with usser password
        return AES.encrypt(headerPassword, rawHeader);

    }

    public String getEncryptionPassword() {
        return encryptionPassword;
    }

    public int getBinarySize() {
        return binarySize;
    }

    public String getRawDataMD5() {
        return rawDataMD5;
    }

    public void setRawDataMD5(String rawDataMD5) {
        this.rawDataMD5 = rawDataMD5;
    }

    public String getHeaderPassword() {
        return headerPassword;
    }

    public void setHeaderPassword(String headerPassword) {
        this.headerPassword = headerPassword;
    }

    public int getCompressed() {
        return compressed;
    }

    public void setCompressed(int compressed) {
        this.compressed = compressed;
    }

    public String getStegoAlgorithm() {
        return stegoAlgorithm;
    }

    public void setStegoAlgorithm(String stegoAlgorithm) {
        this.stegoAlgorithm = stegoAlgorithm;
    }
    
    

}
