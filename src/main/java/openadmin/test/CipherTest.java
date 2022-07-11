package openadmin.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import openDao.util.cipher.CipherUtils;

public class CipherTest {

    @Test
    public void testCipher() throws FileNotFoundException {
    	
        CipherUtils cipher = new CipherUtils();
        
        cipher.cipherPrincipal("D:/cipher/", "auth.csv.cipher", 2);
		
		List<String> result = new ArrayList<String>();
	
		File myObj = new File("D:/cipher/auth.csv.cipher.decipher");
		
		
		   Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        result.add(new String(data));
		      }
		
		      myReader.close();
		      myObj.delete();
		
      
        assertEquals("admin", result.get(0));
        assertEquals("hola132", result.get(1));
    }
}