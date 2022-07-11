package openadmin.widgets.jsf.component;

import java.io.Serializable;

//import javax.faces.application.Application;
//import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
//import org.richfaces.component.html.HtmlCalendar;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;

/**
 *  Class that defines the calendar component 
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  10-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class TplCalendar implements Serializable{
	
	private static final long serialVersionUID = 16051001L;

	public static Calendar calendar01(String value, Class<?> typeClass){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		Calendar calendar = new Calendar();
		calendar.setLocale("es");
		calendar.setPattern("dd/MM/yyyy");
		calendar.setShowButtonPanel(true);
		calendar.setShowOn("button");
		
		calendar.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
		
		return calendar;
	
	}
	
}
