//package openadmin.widgets.jsf.component;
//
//import java.io.Serializable;
//
//import org.primefaces.component.fileupload.FileUpload;
//
//import jakarta.faces.application.Application;
//import jakarta.faces.context.FacesContext;
//import openDao.util.messages.MessagesTypes;
//
////import javax.faces.application.Application;
////import javax.faces.context.FacesContext;
////
////import openadmin.util.messages.MessagesTypes;
////
////import org.richfaces.component.html.HtmlFileUpload;
////import org.richfaces.event.UploadEvent;
//
//public class TplFileUpload implements Serializable {
//	
//	private static final long serialVersionUID = 18121001L;
//	
//	@SuppressWarnings("deprecation")
//	public static FileUpload fileUpload(){
//		
//		Application app = FacesContext.getCurrentInstance().getApplication();
//		
//		FileUpload load = new FileUpload();
//		load.setSizeLimit((long) 1);
//		load.setLabel(MessagesTypes.msgOperation("search"));
//		load.setCancelLabel(MessagesTypes.msgOperation("delete"));
//		//load.setClearAllControlLabel(MessagesTypes.msgOperation("delete"));
//		load.setUploadLabel(MessagesTypes.msgOperation("carga"));
//		//load.setDoneLabel(MessagesTypes.msgOperation("cargado"));
//		load.setImmediate(true);		
//		
//		load.setListener(app.
//		 createMethodBinding("#{ctx.listenerLoadDisk}", new Class[] {UploadEvent.class}));
//
//		return load;
//	}
//
//}
