package openadmin.widgets.jsf.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import jakarta.faces.application.Application;
import jakarta.faces.component.UISelectItem;
import jakarta.faces.context.FacesContext;

//import javax.faces.application.Application;
//import javax.faces.component.UISelectItem;
//import javax.faces.component.UISelectItems;
//import javax.faces.context.FacesContext;

//import openadmin.dao.Base;
//
//import org.jboss.seam.annotations.In;
//import org.richfaces.component.html.HtmlComboBox;

import jakarta.inject.Inject;
import openDao.action.ContextAction;

/**
 *  Class that defines the combobox component 
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  10-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class TplComboBox implements Serializable{

	/** Field that contain the context*/
	@Inject
	private static ContextAction ctx;
	
	private static List <String> lst = new ArrayList<String>();
	
	public static SelectOneMenu HtmlComboBox01(String pList, String value){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		SelectOneMenu combo = new SelectOneMenu();
				
		combo.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, String.class));
	
		if (pList.equals("languages")) {
			
			lst = languages();
		}
		
		// Add SelectItems To Data type ComboBox. 
		UISelectItem selectItems = new UISelectItem(); 
		//selectItems.setValueExpression("value", app.getExpressionFactory().createValueExpression( 
		//								lst, List.class)); 
		//combo.getChildren().add(selectItems); 
		
		return combo;
	
	}
	
	private static List languages(){
		
		List <String> lang = new ArrayList<String>();
		
		lang.add("es");
		lang.add("ca");
		//ctx.getConn().findObjectDescription();
		
		return lang;
		
	}
	
}
