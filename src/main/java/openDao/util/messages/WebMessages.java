package openDao.util.messages;

import java.io.Serializable;
import java.text.MessageFormat;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 * Class that contains the definition of various error messages 
*/
public class WebMessages implements Serializable{
	
	private static final long serialVersionUID = 16051001L;
	
	public static void messageError(String pMessage) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		message.setDetail(MessagesTypes.msgError(pMessage));
		message.setSeverity(message.SEVERITY_ERROR);
		context.addMessage("ERROR:", message);
		
	}
	
	public static void messageErrorDao(String pMessage) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		message.setDetail(pMessage);
		message.setSeverity(message.SEVERITY_ERROR);
		context.addMessage("ERROR:", message);
		
	}

	public static void messageErrorParam(String pMessage, Object[] pParams) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		
		String param = MessageFormat.format(MessagesTypes.msgError(pMessage),pParams);
		message.setDetail(param);
		message.setSeverity(message.SEVERITY_ERROR);
		context.addMessage("ERROR:", message);
		
	}
	
	public static void messageInfo(String pMessage) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		message.setDetail(MessagesTypes.msgOperationWeb(pMessage));
		message.setSeverity(message.SEVERITY_INFO);
		context.addMessage("INFO:", message);
		
		
	}
	
}
