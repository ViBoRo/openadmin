package openDao.util.cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

public class CipherUtils implements CipherFacade {
	
	
	/**
	*	Method for encrypting or decrypting a textfile using the AES block cipher algorithm.
	*   The PaddedBufferedBlockCipher outputs a block only when the buffer is full and more data is being added,
	* 	or on a doFinal (unless the current block in the buffer is a pad block). The default padding mechanism used
	* 	is PKCS5/PKCS7.
	*   pots consultar els enllaços.
	*	@param path Ruta del fitxer
	*	@param pNameFile Nom del fitxer
	*	@param mode Mode de la funció 1->encriptar 2->desencriptar
	*	@see <a href = "https://www.tutorialspoint.com/cryptography/advanced_encryption_standard.htm" > tutorialspoint.com – Advanced encryption standard </a>
	*	@see <a href = "https://www.ibm.com/docs/en/zos/2.1.0?topic=rules-pkcs-padding-method" > ibm.com – PKCS padding method </a>
	*/
	public void cipherPrincipal(String path, String pNameFile, int mode) {
		
		//Plane text
		byte[] text;
		
		//Key
		byte[] ca = null;
		
		//Initiation vector
		byte[] iv = null;
		
		boolean typeCipher = true;
		
		String strLine = ""; 
		
		try {
		
			//Read file 
			FileReader inputData = new FileReader(path + pNameFile);
			FileWriter outputData = null;
			
			BufferedReader bufferIn = new BufferedReader(inputData);
			BufferedWriter bufferOut = null;	
			
			// Cipherer creation and configuraton
		    BlockCipher engine = new AESEngine();
		    BlockCipher cipherInOperationMode = new CBCBlockCipher(engine);
		    PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cipherInOperationMode, new PKCS7Padding());			 
		     
			//Encript mode
			if (mode == 1) {
								
				outputData = new FileWriter (path + pNameFile + ".cipher");
				bufferOut = new BufferedWriter(outputData);
				
				//Generating random key and vector 
				ca = KeySecureRandom(32);
				iv = KeySecureRandom(16);
				
				bufferOut.write(Hex.toHexString(iv) + "\n");
				bufferOut.write(Hex.toHexString(ca) + "\n");
			
			} 
			 
			//Decript mode
			if (mode == 2) {
				
				typeCipher = false;
				
				
				outputData = new FileWriter (path + pNameFile + ".deCipher");
				bufferOut = new BufferedWriter(outputData);
				
				//Read keys from encripted file
				iv = Hex.decode(bufferIn.readLine());	
				ca = Hex.decode(bufferIn.readLine());
			}
			
			 KeyParameter keyParameter = new KeyParameter(ca);
			 ParametersWithIV parametersWithIV = new ParametersWithIV(keyParameter, iv);
			
			 if (mode == 1) {
				// Init the cipherer with the key and initialization vector
				 cipher.init(typeCipher, parametersWithIV);
				 
			 } else if (mode == 2){				
				 // Reuse the cipherer to do the deciphering with the cipher key and recovered iv
				 cipher.reset();
				 cipher.init(false, parametersWithIV); 			 
			 }
			 
			 while (true) {
					
				strLine = bufferIn.readLine();
					
				if (strLine == null) {
					bufferIn.close();
					bufferOut.close();
					break;  			 			
				}
				
				
				//
				//Encript mode
				if (mode == 1) {
							
					text = strLine.getBytes("utf-8");
					
					//Output cipher text
					byte[] outputCipherText =  new byte [cipher.getOutputSize(text.length)];
				   				
				    // Do the ciphering 
					//Lenght output cipher text
					int outputCipherTextLenght = cipher.processBytes(text, 0, text.length, outputCipherText, 0);
				    
					outputCipherTextLenght += cipher.doFinal(outputCipherText, outputCipherTextLenght);
			        		        			    
			        bufferOut.write(Hex.toHexString(outputCipherText,0,outputCipherTextLenght) + "\n");
						
				}
				
				//Decript mode
				else if (mode == 2) {
					
					 byte[] cipherText = Hex.decode(strLine.toUpperCase()); 
					 
					 // Allocate space for the deciphered text
					 byte[] deciphered = new byte[strLine.length()];
				
					 // Perform the deciphering
					 int finalDecipheredTextSize = cipher.processBytes(cipherText, 0, cipherText.length, deciphered, 0);
					 finalDecipheredTextSize += cipher.doFinal(deciphered, finalDecipheredTextSize);
								
					 byte[] outputTextDeciphe= new byte[finalDecipheredTextSize];
				  
					 System.arraycopy(deciphered, 0, outputTextDeciphe, 0,finalDecipheredTextSize);
				
				     bufferOut.write(new String(outputTextDeciphe, "utf-8") + "\n");
				}
				
			 }
			 
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.out.println("File not found");
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Character not found");
		
		} catch (DataLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	

	/**
	*	Method for encrypting a text file
	*	@param path File location path
	*	@param pNameFile File name
	*/
	public void encriptFile(String path, String pNameFile) {
		
		try {
		
			generateKeySecureRandom(32);
			
			//Key
			byte[] ca = KeySecureRandom(32);
			//Vector
			byte[] iv = KeySecureRandom(16);
			
			//Plane text
			byte[] text;
			
			//Output cipher text
			byte[] outputCipherText;
			
			//Lenght output
			int finalOutputSize;
			
			//Result
			//byte[] result;
			
			FileReader inputData = new FileReader(path + pNameFile);
			FileWriter outputData = new FileWriter (path + pNameFile + ".cipher");
			
			BufferedReader bufferIn = new BufferedReader(inputData);
			BufferedWriter bufferOut = new BufferedWriter(outputData);
			
			String strLine = "";
			
			 // Create the cipherer
		     BlockCipher engine = new AESEngine();
		     BlockCipher cipherInOperationMode = new CBCBlockCipher(engine);
		     PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cipherInOperationMode, 
		        new PKCS7Padding());
				    			
			bufferOut.write(Hex.toHexString(ca) + "\n");
			bufferOut.write(Hex.toHexString(iv) + "\n");

		    //bufferOut.write(BaseEncoding.base16().encode(key) + "\n");
		    //bufferOut.write(BaseEncoding.base16().encode(iv) + "\n");
			
			// Init the cipherer with the key and initialization vector
		    KeyParameter keyParameter = new KeyParameter(ca);
		    ParametersWithIV parametersWithIV = new ParametersWithIV(keyParameter, iv);
		    cipher.init(true, parametersWithIV);

			while (true) {
				
				strLine = bufferIn.readLine();
				
				if (strLine == null) {
					bufferIn.close();
					bufferOut.close();
					break;  			 			
				}
				
				text = strLine.getBytes("utf-8");
				
				// Allocate space for maximum output size when creating output array
				outputCipherText =  new byte [cipher.getOutputSize(text.length)];
			   				
			    // Do the ciphering
			    finalOutputSize = cipher.processBytes(text, 0, text.length, outputCipherText, 0);
			    
			    finalOutputSize += cipher.doFinal(outputCipherText, finalOutputSize);
			    
		        //result = new byte[iv.length + finalOutputSize];
			    //result = new byte[finalOutputSize];
		        		        
	        
		        //System.arraycopy(iv, 0, result, 0, iv.length);		        
		        //System.arraycopy(outputCipherText, 0, result, 0, result.length);
		        //System.arraycopy(outputCipherText, 0, result, iv.length, finalOutputSize);
		        
		        //bufferOut.write(BaseEncoding.base16().encode(result) + "\n");
		        //bufferOut.write(Hex.toHexString(result,0,result.length) + "\n");
		        bufferOut.write(Hex.toHexString(outputCipherText,0,finalOutputSize) + "\n");
									
			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.out.println("File not found");
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Character not found");
		
		} catch (DataLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	

	/**
	*	Method for decrypting a file using the AES block cipher algorithm and returning the decrypted content.
	*	The PaddedBufferedBlockCipher outputs a block only when the buffer is full and more data is being added,
	* 	or on a doFinal (unless the current block in the buffer is a pad block). The default padding mechanism used
	* 	is the one outlined in PKCS5/PKCS7. 
	*	@param path File location path
	*	@param pNameFile File name
	*	@return List[] - List of strings where each element is a decrypted block of the text file.
	*	 
	*/
	
	public List<String> decripFileProperties(String path, String pNameFile) {
		
		List<String> result = new ArrayList<String>();
		
		//Plane text
		byte[] text;
		
	
		byte[] ca = null;
		byte[] iv = null;
		
		
		boolean typeCipher = false;
		
		String strLine = ""; 
		
		try {
		
			FileReader inputData = new FileReader(path + pNameFile);
			
			BufferedReader bufferIn = new BufferedReader(inputData);
			
			// Create the cipherer
		    BlockCipher engine = new AESEngine();
		    BlockCipher cipherInOperationMode = new CBCBlockCipher(engine);
		    PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cipherInOperationMode, new PKCS7Padding());
		    
		    iv = Hex.decode(bufferIn.readLine());	
			ca = Hex.decode(bufferIn.readLine());
			
			KeyParameter keyParameter = new KeyParameter(ca);
			ParametersWithIV parametersWithIV = new ParametersWithIV(keyParameter, iv);
			
			cipher.reset();
			cipher.init(false, parametersWithIV); 
			
			 while (true) {
					
				strLine = bufferIn.readLine();
						
				if (strLine == null) {
					bufferIn.close();
					break;  			 			
				}
				
				byte[] cipherText = Hex.decode(strLine.toUpperCase()); 
				 
				// Allocate space for the deciphered text
				byte[] deciphered = new byte[strLine.length()];
			
				// Perform the deciphering
				int finalDecipheredTextSize = cipher.processBytes(cipherText, 0, cipherText.length, deciphered, 0);
				finalDecipheredTextSize += cipher.doFinal(deciphered, finalDecipheredTextSize);
							
				byte[] outputTextDeciphe= new byte[finalDecipheredTextSize];
			  
				System.arraycopy(deciphered, 0, outputTextDeciphe, 0,finalDecipheredTextSize);
				
				result.add(new String(outputTextDeciphe, "utf-8"));
													
			 }
		    
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		
		} catch (IOException e) {

			e.printStackTrace();
		} catch (DataLengthException e) {

			e.printStackTrace();
		
		} catch (IllegalStateException e) {

			e.printStackTrace();
		
		} catch (InvalidCipherTextException e) {
		
			e.printStackTrace();
		}

		
		return result;
		

	}
	
	/**
	*	Method for encrypting a text file
	*	@param path File location path
	*	@param pNameFile File name
	*/
	public void decriptFile(String path, String pNameFile) {
		
		byte[] ca = null;
		byte[] iv = null;
				
		boolean typeCipher = false;
				
		String strLine = ""; 
		
		try {
	
			FileReader inputData = new FileReader(path + pNameFile);
		
			BufferedReader bufferIn = new BufferedReader(inputData);
		
			ca = Hex.decode(bufferIn.readLine());
			iv = Hex.decode(bufferIn.readLine());		
			
			 // Create the cipherer
			 BlockCipher engine = new AESEngine();
			 BlockCipher cipherInOperationMode = new CBCBlockCipher(engine);
			 PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cipherInOperationMode, new PKCS7Padding());
		
			// Reuse the cipherer to do the deciphering with the cipher key and recovered iv
			 KeyParameter decipherKey = new KeyParameter(ca);
			 ParametersWithIV parametersWithIV = new ParametersWithIV(decipherKey, iv);
			 cipher.reset();
			 cipher.init(false, parametersWithIV);		 
			 
			 while (true) {
			
				 strLine = bufferIn.readLine();
			 
				 if (strLine == null) {
					bufferIn.close();
					break;  			 			
				 }
			 
				 byte[] cipherText = Hex.decode(strLine.toUpperCase()); 
				 
				 // Allocate space for the deciphered text
				 byte[] deciphered = new byte[strLine.length()];
			
				 // Perform the deciphering
				 int finalDecipheredTextSize = cipher.processBytes(cipherText, 0, cipherText.length, deciphered, 0);
				 finalDecipheredTextSize += cipher.doFinal(deciphered, finalDecipheredTextSize);
							
				 byte[] outputTextDeciphe= new byte[finalDecipheredTextSize];
			  
				 System.arraycopy(deciphered, 0, outputTextDeciphe, 0, finalDecipheredTextSize);
			
				 System.out.println(new String(outputTextDeciphe, "utf-8"));
				 
			     //bufferOut.write(new String(outputTextDeciphe, "utf-8") + "\n");
			}
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 
	}
	
	/**
	*	Generate a random key using a string as a parameter
	*	@param password Word used for generating a key
	*	@return byte[] - Returns the key generated in bytes
	*/
	public byte[] cipherPw(String password) {
		
		SecureRandom randomGenerator = new SecureRandom();
	    byte[] key = password.getBytes();
	    
	    randomGenerator.nextBytes(key);
	 
		return key;
	}
	
	/**
	*	Generate a random key 
	*	@return key - Returns the key object
	*/
	private Key generateKey() {
		
		 // La clave secreta se genera automáticamente
		KeyGenerator keyGenerator;
		Key key = null;
		try {
			
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			key = keyGenerator.generateKey();
			
			
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		
		return key;
		
	}
	
	
	/**
	*	Generates a key using a byte chain as a parameter
	*	@param pNumBytes Chain of bytes used to generate the key
	*	@return byte[] - Returns the generated key in bytes
	*/
	private byte[] KeySecureRandom(int pNumBytes) {
		
		 // La clave secreta se genera automáticamente
		SecureRandom randomGenerator = new SecureRandom();
	    byte[] key = new byte[pNumBytes];              
	   
	    randomGenerator.nextBytes(key);
	 
		return key;
		
	}
	
	/**
	*	Generates a key using a byte chain as a parameter
	*	@param pNumBytes Chain of bytes used to generate the key
	*	@return byte[] - Returns the generated key object
	*/
	private Key generateKeySecureRandom(int pNumBytes) {
		
		 // La clave secreta se genera automáticamente
		KeyGenerator keyGenerator;
		
		SecureRandom randomGenerator = new SecureRandom();              	   
	    randomGenerator.nextBytes(new byte[pNumBytes]);
		
		Key key = null;
		try {
			
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(randomGenerator);
			key = keyGenerator.generateKey();
			
			System.out.println(key.toString().length());
			System.out.println(key.toString());
			
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		
		return key;
		
	}

}
