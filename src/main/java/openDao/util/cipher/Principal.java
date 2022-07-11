package openDao.util.cipher;

public class Principal {

	public static void main(String[] args) {
		
		CipherFacade cipher = new CipherUtils();
		
		//cipher.cipherPrincipal("D:/ProjectAjuntament/", "probes.txt", 1);
		
		//cipher.cipherPrincipal("D:/eclipse-workspace/CoreCipher/lib/src/main/java/connectiondb/", "persistence.csv", 1);
		
		cipher.cipherPrincipal("D:/cipher/", "auth.csv", 1);
	}

}
