/**
 *  __  _____  ____  __    ___  _____  ___   ___   _    
 * ( (`  | |  | |_  / /`_ / / \  | |  / / \ / / \ | |   
 * _)_)  |_|  |_|__ \_\_/ \_\_/  |_|  \_\_/ \_\_/ |_|__  
 * ---------------------------------------------------- 
 * 
 * @author dnllns
 * @version v1.0 java
 * @since early 2020 
 * @see Source available on https://github.com/Dnllns/stegotool-java 
 * @see Based on Estegomaquina-Android, https://github.com/Dnllns/EstegoMaquina-Android
 *
 */

package stegotool;

import encryption.AES;
import encryption.Md5;

public class Header {
   
	private Payload payload;
	// Pasword fot encrypt/decrypt payload
	// Only for more security. 
	// Must be random generation    
    private String payloadEncryptionPassword;  
    // Pasword for decrypt encrypted-header (Master password for final user)
    // The only password needed by the user
    private String headerPassword; 
    // Encrypted header, is need for extraction
    private String encryptedHeader;

    
    //CONSTRUCTORES
    //----------------------
    
    /**
     * Constructor para la inserccion
     * @param payload
     * @param password
     */
    public Header(Payload payload, String password ) {
    	this.payload=payload;
    	this.headerPassword=password;
    }
   
    /**
     * Constructor para la extraccion
     * @param encryptedHeader, Raw string encrypted-header
     * @param headerPassword,  Password for decrypt encrypted-header
     */
    public Header(final String encryptedHeader, final String headerPassword) {
        this.encryptedHeader = encryptedHeader;
        this.headerPassword = headerPassword;
    }
    
    /**
     * Genera la cabecera a parrtir de la configuracion y el payload
     * @return 
     */
    public String make() {
    	
    	//Generar la cabecera
    	String rawHeader = payloadEncryptionPassword 
				+ ";" + Md5.getMD5(payload.getPayload())  
				+ ";" + Config.stegoAlgorithm;;	
		//*Control del 'metodo de frenado' usado
    	if (Config.usingPayloadSize) {
    		rawHeader = rawHeader + ";" + payload.getSize();
    	}
    	else if (Config.usingStopCode) {
    		rawHeader = rawHeader + ";" + Config.stopCode;    		
    	}
        //Devolver la cabecera encriptada
        return AES.encrypt(rawHeader, headerPassword);
    }

    /**
     * Decrypt encrypted header and load data into structures
     */
    public String[] decrypt() {

    	String headerData[] = new String[5];
    	
        final String decrypted = AES.decrypt(encryptedHeader, headerPassword);
        final String[] chunkedHeader = decrypted.split(";");
        
        //payloadEncryptionPassword 
        headerData[0]= chunkedHeader[0];
        //payloadMD5 
        headerData[1]= chunkedHeader[1];        
        //stegoAlgorithm
        headerData[2] = chunkedHeader[2];
        
		//*Control del 'metodo de frenado' usado
        //*En funcion del numero de elementos en data[]
        //*Si hay 4 es usando tamaño, si hay 5 (se añade un campo extra "0"), es usando stopCode
    	if (headerData.length == 4) {
    		headerData[3] = chunkedHeader[3];
    	}
    	else if (chunkedHeader.length == 5 && chunkedHeader[3].equals("0")) {
    		headerData[3] = chunkedHeader[4];    		
    	}
  
    	return headerData;
    }
 

}
