package openadmin.widgets.jsf.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;

import jakarta.persistence.Transient;
import openDao.annotation.NoSql;
import openDao.annotation.NotList;
import openDao.model.Base;
import openDao.util.messages.MessagesTypes;

//import javax.faces.component.html.HtmlPanelGrid;
//import javax.persistence.Transient;
//
//import openadmin.annotations.NoSql;
//import openadmin.annotations.NotList;
//import openadmin.dao.Base;
//import openadmin.util.messages.MessagesTypes;
//
//import org.ajax4jsf.component.html.HtmlAjaxCommandButton;
//import org.hibernate.validator.Length;


/**
 *  Class that defines the modal panel types  
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  10-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class TplModalPanel extends TplModalPanelParent implements Serializable {

	private static final long serialVersionUID = 23031001L;
	
	
	public void modalPanel01(String pHeader, List<Base> lstbase,
									String pTypeModelList, String objectOut){
		
		closedModalPanel("cmdmpsearchall");
		super.execute(pHeader, "mpsearchall", lengthModalPanel(lstbase.get(0)));
		
		CommandButton cmd = TplSimpleComponent.commandFr1("Cerrar", "#{ctx.getView(1).closedModalPanel}", "cmd" + modalPanel.getId() ,"");
	        
	    modalPanel.getChildren().add(cmd);
		
		if (pTypeModelList.equals("default")) {			
			
			TpListDefault dataTable = new TpListDefault();			
			modalPanel.getChildren().add(dataTable.execute(lstbase, objectOut));
		
		}
	
		super.frm.getChildren().add(modalPanel);
	}
	
	public void modalPanel02(String pHeader, List<Base> lstbase,
			String pTypeModelList, String objectOut, String pAccio){

		closedModalPanel("cmdmpsearchall");
		super.execute(pHeader, "mpsearchall", lengthModalPanel(lstbase.get(0)));

		CommandButton cmd = TplSimpleComponent.commandFr1("Cerrar", "#{ctx.getView(1).closedModalPanel2}", "cmd" + modalPanel.getId(), pAccio);

		modalPanel.getChildren().add(cmd);

		if (pTypeModelList.equals("default")) {			

			TpListDefault dataTable = new TpListDefault();			
			modalPanel.getChildren().add(dataTable.execute(lstbase, objectOut));

		}

		super.frm.getChildren().add(modalPanel);
	}
	
	public void modalPanelYesNo(String pAction, String objectDestination){

		super.execute(MessagesTypes.msgOperationWeb(pAction), "mpyesno", 200);
		System.out.println("S'esta obrint el modal panel: " + pAction);
		modalPanel.getChildren().add(TplSimpleComponent.HtmlOutputText01(MessagesTypes.msgOperationWeb("operation_" + pAction)));
		
		PanelGrid grid = new PanelGrid();
		
		grid.setColumns(2);
		
		CommandButton cmdSi = TplSimpleComponent.commandFr1("Si", objectDestination, "idYes" + pAction ,"");		
		CommandButton cmdNo = TplSimpleComponent.commandFr1("No", objectDestination, "idNo" + pAction ,"");
		
		grid.getChildren().add(cmdSi);
		grid.getChildren().add(cmdNo);
		
		modalPanel.getChildren().add(grid);

		super.frm.getChildren().add(modalPanel);
		
	}
	
	public void modalPanelInfo(String pHeader, PanelGrid pGrid){
		
		closedModalPanel("cmdmpinfo");
		
		super.execute(pHeader, "mpinfo", 500);
		
		CommandButton cmd = TplSimpleComponent.commandFr1("Cerrar", "#{ctx.getView(1).closedModalPanel}", "cmd" + modalPanel.getId() ,"");
		
		modalPanel.getChildren().add(cmd);
		modalPanel.getChildren().add(pGrid);
		 
		 super.frm.getChildren().add(modalPanel);
		
	}
	
	private int lengthModalPanel(Base pBase){			
		
		
		int res = 0;
		
		for (Field f: pBase.getClass().getDeclaredFields()){		
			
			//Exclusions
			if (	f.getName().equals("debuglog") ||
					f.getName().equals("historiclog") ||
					f.getName().equals ("serialVersionUID") || 
					f.isAnnotationPresent(Transient.class) ||
					f.isAnnotationPresent(NotList.class) ||
					f.isAnnotationPresent(NoSql.class) ||
					f.getName().equals("password"))
					continue;
			
			//View of fields 
			f.setAccessible(true);
			
			//System.out.println(f.getType().getSimpleName());
			
			if (f.getType().getSimpleName().endsWith("String")) res = res + f.getAnnotation(Length.class).max() + 180;
															
			else if (f.getType().getSimpleName().endsWith("Integer")) res = res + 30;
			
			else if (f.getType().getSimpleName().endsWith("Date")) res = res + 20;
			
			else if (f.getType().getSimpleName().endsWith("Boolean")) res = res + 15;
			
			else if (f.getType().getName().startsWith("openadmin.model")) res = res + 180;

			//System.out.println("Tamany modalpanel " + res);
			
			/**
			if (f.getName().equals("description")){
			
				res = 35 * f.getAnnotation(Length.class).max();
			}*/
		}
		
		return res;
	}

}
