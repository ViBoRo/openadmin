package openadmin.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bouncycastle.util.encoders.Hex;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import openDao.action.ContextAction;
import openDao.connection.ConnectionDao;
import openDao.operation.DaoJpaHibernate;
import openDao.operation.DaoOperationFacade;
import openDao.util.cipher.CipherUtils;
import openadmin.util.data.InitDataLoad;


/**
 *  Control entity that links the role, menuitem and action entities
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  13-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
@Named
@SessionScoped
public class DaoPrincipal implements Serializable {

	private static final long serialVersionUID = 6032201L;
	
	@Getter @Setter
	private String nameDatabase;
	
	@Getter @Setter
	private String user;
	
	@Getter @Setter
	private String pass;
	
	@Inject
	private ContextAction ctx;
	
	/**
	public static void main(String[] args) throws ClassNotFoundException {
		
		System.out.print("Classe Principal Dao");
	
		String user = "Alfred";
		
		String program = "test";
				
		DaoOperationFacade dao = new DaoJpaHibernate 
				(new ConnectionDao().getEntityManagerDao("log_post"), "ca", "alfred", "control");
			
		dao.begin();
			
		dao.isAudit(true);
		
		/**
		Usuari usu = new Usuari();
		usu.setDescription("Alfred");
		usu.setNom("Alfred Oliver");
		
		dao.persistObject(usu);
		
		usu.setNom("Alfred Oliver Company");
		
		dao.updateObject(usu);
		
		
		dao.isAudit(true);
		
		dao.commit();
		
	}*/
	
	public void conectar() {
		
		System.out.println("conectar a: " + nameDatabase);
				
		try {
//			DaoOperationFacade dao = new DaoJpaHibernate 
//					(new ConnectionDao().getEntityManagerDao("log_post"), "ca", "alfred", "control");
			InitDataLoad init = new InitDataLoad();
			init.initializeData();
			
			User useraux = new User();
			useraux.setDescription(this.user);
			useraux.setPassword(this.pass);
			ctx.setUser(useraux);
			ctx.login();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public String autenticar() throws FileNotFoundException{
		
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
	      
		 System.out.println("user: " + this.user);
		 System.out.println("pass: " + this.pass);
		 
		 System.out.println("user: " + result.get(0));
		 System.out.println("pass: " + result.get(1));
		 
		 if(this.user.equals(result.get(0)) && this.pass.equals(result.get(1))) {
			 System.out.println("autenticacion correcta");
			 conectar();
			 return "home.xhtml";
		 }else {
			 return "login.xhtml";
			 }	  
}

}