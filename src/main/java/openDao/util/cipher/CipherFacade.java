package openDao.util.cipher;

import java.util.List;

/**
* 	Interface que gestiona el cifrat y descifrat des fitxers
*
*	@version 1.0; 11-06-2021;
*	@author Alfredo Oliver Company
*/
public interface CipherFacade {

	/**
	*	Method for encrypting a password
	*	@param password Word to encrypt
	*	@return byte[] - Returns encrypted password
	*/
	public byte[] cipherPw(String password);
	
	/**
	*   Method for encrypting or decrypting a textfile using the AES block cipher algorithm.
	*   The PaddedBufferedBlockCipher outputs a block only when the buffer is full and more data is being added,
	* 	or on a doFinal (unless the current block in the buffer is a pad block). The default padding mechanism used
	* 	is PKCS5/PKCS7.
	*	@param path File location path
	*	@param pNameFile File name
	*	@param pMode 1-> encrypt, 2-> decrypt
	*	@see <a href = "https://www.tutorialspoint.com/cryptography/advanced_encryption_standard.htm" > tutorialspoint.com – Advanced encryption standard </a>
	*	@see <a href = "https://www.ibm.com/docs/en/zos/2.1.0?topic=rules-pkcs-padding-method" > ibm.com – PKCS padding method </a>
	*/
	public void cipherPrincipal(String path, String pNameFile, int pMode);
	
	/**
	*	Method for decrypting a file and returning the decrypted content using the AES block cipher algorithm.
	*	The PaddedBufferedBlockCipher outputs a block only when the buffer is full and more data is being added,
	* 	or on a doFinal (unless the current block in the buffer is a pad block). The default padding mechanism used 
	* 	is PKCS5/PKCS7.
	*	@param path File location path
	*	@param pNameFile File name
	*	@return List[] - Returns a list of strings where each element is a decrypted block of the text file. 
	*/
	public List<String> decripFileProperties(String path, String pNameFile);
}
