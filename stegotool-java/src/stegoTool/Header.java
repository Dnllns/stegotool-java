package stegoTool;

import stegoTool.encryption.AES;

public class Header {

    //Encoding data
    private int payloadBinarySize;      //Payload binary size
    private String payloadMD5;          //MD5 hash of payload
    private String encryptionPassword;  //Pasword for encrypt raw header (Must be random generation)
    private int compressed;             //Encrypted-header 'is compresed' flag
    private String stegoAlgorithm;      //Used steganography algorithm
    //Decoding data
    private String encryptedHeader;     //Encrypted-header
    //Comomn data
    private String headerPassword;      //Pasword for decrypt encrypted-header


    /**
     * Constructor for decoding action
     * @param encryptedHeader, Raw string encrypted-header
     * @param headerPassword, Password for decrypt encrypted-header
     */    
    public Header(final String rawEncryptedHeader, final String headerPassword) {
        this.encryptedHeader = rawEncryptedHeader;
        this.headerPassword = headerPassword;       
    }

    /**
     * Constructor for encoding action
     * @param payloadBinarySize 
     * @param payloadMD5
     * @param encryptionPassword
     * @param headerPassword
     * @param compressed
     * @param stegoAlgorithm
     */
    public Header(
            final int payloadBinarySize,
            final String payloadMD5,
            final String encryptionPassword,
            final String headerPassword,
            final boolean compressed,
            final String stegoAlgorithm
    ) {
        this.payloadBinarySize = payloadBinarySize;
        this.payloadMD5 = payloadMD5;
        this.encryptionPassword = encryptionPassword;
        this.headerPassword = headerPassword;
        this.stegoAlgorithm = stegoAlgorithm;
        if (compressed) {
            this.compressed = 1;
        } else {
            this.compressed = 0;
        }

    }


    public String make() {

        final String rawHeader = encryptionPassword + ";"
                + payloadMD5 + ";"
                + payloadBinarySize + ";"
                + compressed + ";"
                + stegoAlgorithm;

        //Encrypted header with usser password
        return AES.encrypt(rawHeader, headerPassword);

    }




    /**
     * Decrypt encrypted header and load data into structures
     */
    private void decrypt(){

        final String decrypted = AES.decrypt(encryptedHeader, headerPassword);
        final String[] parts = decrypted.split(";");
        encryptionPassword = parts[0];
        payloadMD5 = parts[1];
        payloadBinarySize = Integer.parseInt(parts[2]);
        compressed = Integer.parseInt(parts[3]);
        stegoAlgorithm = parts[4];
    }


    //Getter & Setter methods

    public String getEncryptionPassword() {
        return encryptionPassword;
    }

    public int getRawDataBinarySize() {
        return payloadBinarySize;
    }

    public String getRawDataMD5() {
        return payloadMD5;
    }

    public void setRawDataMD5(final String payloadMD5) {
        this.payloadMD5 = payloadMD5;
    }

    public String getHeaderPassword() {
        return headerPassword;
    }

    public void setHeaderPassword(final String headerPassword) {
        this.headerPassword = headerPassword;
    }

    public int getCompressed() {
        return compressed;
    }

    public void setCompressed(final int compressed) {
        this.compressed = compressed;
    }

    public String getStegoAlgorithm() {
        return stegoAlgorithm;
    }

    public void setStegoAlgorithm(final String stegoAlgorithm) {
        this.stegoAlgorithm = stegoAlgorithm;
    }
    
    

}
