/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stegoTool;

import stegoTool.encryption.AES;

/**
 *
 * @author Casa
 */
public class Header {

    private int binarySize;
    private String rawDataMD5;
    private String encryptionPassword;
    private String headerPassword;
    private int compressed;

    public Header(int binarySize, String rawDataMD5, String encryptionPassword, String headerPassword, boolean compressed) {
        this.binarySize = binarySize;
        this.rawDataMD5 = rawDataMD5;
        this.encryptionPassword = encryptionPassword;
        this.headerPassword = headerPassword;
        if (compressed) {
            this.compressed = 1;
        }

    }

    public String makeHeader() {

        String rawHeader = encryptionPassword + ";"
                + rawDataMD5 + ";"
                + binarySize + ";" + compressed;

        //Encrypted header with usser password
        return AES.encrypt(headerPassword, rawHeader);

    }

    public String getEncryptionPassword() {
        return encryptionPassword;
    }
    
    

}
