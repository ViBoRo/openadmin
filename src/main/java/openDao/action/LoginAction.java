package openDao.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import openDao.util.cipher.CipherUtils;
import openDao.util.messages.WebMessages;
import openadmin.model.User;
import openadmin.util.data.InitDataLoad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 *  Class that contains the definition of the user identification methods
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  25-03-2022
 *  @author Vicent Borja
*/
@Named("loginAction")
@SessionScoped
public class LoginAction implements Serializable {
	
	private static final long serialVersionUID = 23031001L;
	
	@Inject
	private ContextAction ctx;
	

	@Getter @Setter
	private String nameDatabase;
	
	@Getter @Setter
	private String user;
	
	@Getter @Setter
	private String pass;
	
	
	private boolean result;
	/**
	 *  Normal login
	 */
	public void execute() throws ClassNotFoundException{
		
		
		
		
			
			result = false;
			
			/**If user exist*/
			User useraux = new User();
			useraux.setDescription(this.user);
			useraux.setPassword(this.pass);
			ctx.setUser(useraux);
			
			
			
			if (ctx.login()){
				
				FacesContext context = FacesContext.getCurrentInstance();
				
				//Locale default user
				context.getViewRoot().setLocale(new Locale (ctx.getUser().getLanguage()));
				
				result = true;
			  			
			} else {
				
				WebMessages.messageError("error_validation_login");
				
			}
		
	}

	
	
	
	public String login() {
		
		if(this.user.contains("admin")) {
			try {
				if(autenticarAdmin()) {
				try {
					 
					InitDataLoad init = new InitDataLoad();
					init.initializeData();
					System.out.println("autenticacion correcta");
					execute();
					return "home.xhtml";
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return "login.xhtml";
				}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "login.xhtml";
		}else{
				 try {
					 
					InitDataLoad init = new InitDataLoad();
					init.initializeData();
					System.out.println("autenticacion correcta");
					execute();
					return "home.xhtml";
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return "login.xhtml";
				}
		 }
		
		}
		
		
	/**
	 *  Admin login with encrypted file verificaton
	 */
	public boolean autenticarAdmin() throws FileNotFoundException{
		
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
			return true;
		 }	  
		 
		 return false;
}
	
	//getters and setters
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
