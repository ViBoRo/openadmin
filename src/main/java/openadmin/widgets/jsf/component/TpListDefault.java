package openadmin.widgets.jsf.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import org.primefaces.PrimeFaces.Ajax;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;

import jakarta.el.ExpressionFactory;
import jakarta.el.MethodExpression;
import jakarta.el.ValueExpression;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.BehaviorEvent;
import jakarta.persistence.Transient;
import openDao.annotation.NoSql;
import openDao.annotation.NotList;
import openDao.model.Base;
import openDao.util.messages.MessagesTypes;
import openDao.util.messages.WebMessages;

//import javax.faces.component.html.HtmlOutputText;
//import javax.faces.component.html.HtmlSelectBooleanCheckbox;
//import javax.faces.context.FacesContext;
//import javax.persistence.Transient;
//
//import openadmin.annotations.NoSql;
//import openadmin.annotations.NotList;
//import openadmin.annotations.longPanel;
//import openadmin.dao.Base;
//import openadmin.util.messages.MessagesTypes;
//import openadmin.util.messages.WebMessages;
//
//import org.ajax4jsf.component.html.HtmlAjaxSupport;
//import org.hibernate.validator.Length;
//import org.primefaces.component.datatable.DataTable;
//import org.primefaces.component.panel.Panel;
//import org.richfaces.component.html.HtmlColumn;
//import org.richfaces.component.html.HtmlDataTable;
//import org.richfaces.component.html.HtmlDatascroller;
//import org.richfaces.component.html.HtmlPanel;
//import org.richfaces.component.html.HtmlScrollableDataTable;
//import org.richfaces.component.html.HtmlSpacer;


/**
 *  Class that defines the datatable component 
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  10-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class TpListDefault implements Serializable{

	private static final long serialVersionUID = 16051001L;
	
	private Panel outModelList;
	
	private DataTable dataTableMod1;
	private Column column;

	private String baseOut;
	
	private FacesContext context = FacesContext.getCurrentInstance();
	
	public Panel execute(List<Base> lstbase, String objectOut) {
		
		//Create exit component 
		createExitComponent();
		
		if (lstbase.size() == 0) {
			
			WebMessages.messageError("no_records");
			outModelList.setStyleClass("outPanelView");
			return outModelList;
		}
		
		//Result
		int result = 0;
		
		Base base = lstbase.get(0);
		
		baseOut = objectOut;
		
		loadDataTable_mod1(lstbase);
		
		for (Field f: base.getClass().getDeclaredFields()){				
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
			
				result = 1;
				
				//New Column
				column = new Column();
				
				//New column name
				HtmlOutputText head = new HtmlOutputText();
				
				//Value column head
				head.setValue(MessagesTypes.msgLabels(base.getClass().getSimpleName(), f.getName()));
				column.setHeader(head);
				
				//add component to column
				typeComponent(f);
				
				//add column to data table
				dataTableMod1.getChildren().add(column);	
		}
		
		//If no result
		if (result == 0) {
			
			WebMessages.messageError("no_records");
			return outModelList;
			
		}
		
		
		
		//Ajax support
//		AjaxSupport support = new HtmlAjaxSupport();
//		support.setEvent("onRowClick");
//		
//		support.setActionExpression(context.getApplication().getExpressionFactory().createMethodExpression
//							(context.getELContext(), 
//							 objectOut, null ,new Class[]{}));
//		 
//		support.setReRender("idform:idoutDataTplView");
//		//onRowDblClick
//		
//		//Add support to data table
//		
//		dataTableMod1.getChildren().add(support);
		
//		dataTableMod1.setSelection(selection);
//		dataTableMod1.setSelectionMode("single");
//		dataTableMod1.setRowKey("#{pbase.description}");
//		
//		
//		
		
		
		FacesContext fc = FacesContext.getCurrentInstance();
	    ExpressionFactory ef = fc.getApplication().getExpressionFactory();
	    MethodExpression me = ef.createMethodExpression(fc.getELContext(),
	    		objectOut, null, new Class[]{});
	    AjaxBehavior ajaxBehavior = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
		ajaxBehavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(me, me));
		ajaxBehavior.setRender(Collections.singletonList("idform:idoutDataTplView")); 
		dataTableMod1.addClientBehavior("rowSelect", ajaxBehavior);
		
		System.out.println("Finalisim" + dataTableMod1.getRowKey());
		outModelList.getChildren().add(dataTableMod1);
		
		/**
		HtmlAjaxSupport support1 = new HtmlAjaxSupport();
		support1.setId("su_mpsearchall");
		support1.setEvent("onRowDblClick");
		
		//Action
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		MethodExpression methodExpr =  app.getExpressionFactory().createMethodExpression
			(FacesContext.getCurrentInstance().getELContext(),  "#{ctx.getView(1).closedModalPanel}", null ,new Class[]{ javax.faces.event.ActionEvent.class});
		 
		 //Listener
		support1.addActionListener(new MethosdExpressionActionListener(methodExpr));
		
		//Add support to data table
		dataTableMod1.getChildren().add(support1);
		*/
		//Add data table to out panel
		
//		DataTable scrollerDate = new  DataTable();
		
		//outModelList.getChildren().add(scrollerDate);
		
		//Add scroller if number of rows than 10
//		if (lstbase.size() > 10 ){
//			
//			Spacer spacer = new Spacer();
//			spacer.setHeight("30");
//			
//			HtmlDatascroller scroller = new HtmlDatascroller();
//			scroller.setFor("idlist");
//			scroller.setMaxPages(10);
//			scroller.setAlign("left");
//			
//			outModelList.getChildren().add(scroller);
//		
//		}
		
		
		return outModelList;
		
	}

	/**
	 * Get all the fields initialized
	 * @param pType  Identifies the declared type for the field
	 * @param pField field
	 * */
	private void typeComponent (Field pField) {
	
		//If is String	
		if (pField.getType().getSimpleName().endsWith("String") || 
			pField.getType().getSimpleName().endsWith("Integer") ||			
			pField.getType().getSimpleName().endsWith("Date") ||
			pField.getType().getSimpleName().endsWith("Double")) {	
		
		 HtmlOutputText outText = new HtmlOutputText();
		 
		 outText.setValueExpression("value", context.getApplication().getExpressionFactory().createValueExpression(
				 context.getELContext(), 
				 "#{pbase." +  pField.getName() + "}",
				  pField.getType()));
		 System.out.println("#{pbase." +  pField.getName() + "}" + pField.getType());
		 column.getChildren().add(outText);
		
		}
		
		else if (pField.getType().getSimpleName().endsWith("Boolean")){
			
			SelectBooleanCheckbox check = new SelectBooleanCheckbox();
			
			check.setValueExpression("value", context.getApplication().getExpressionFactory().createValueExpression(
					 context.getELContext(), 
					 "#{pbase." +  pField.getName() + "}",
					  pField.getType()));
			
			 column.getChildren().add(check);
		}
	
		//If is Object
		else if (pField.getType().getName().startsWith("openadmin.model")) {
		
			HtmlOutputText outText = new HtmlOutputText();
		 
			outText.setValueExpression("value", context.getApplication().getExpressionFactory().createValueExpression(
				 context.getELContext(), 
				 "#{pbase." +  pField.getName() + ".description}",
				  String.class ));
		
			column.getChildren().add(outText);
		
		}
	
	}
 
	private void createExitComponent(){
		
		outModelList = new Panel();
		outModelList.setId("idlistdefault");	
		outModelList.setStyleClass("listTplView");
		
	}

	private void loadDataTable_mod1(List<Base> lstbase){
	
		//Create data table
		dataTableMod1 = new DataTable();
		dataTableMod1.setId("idlist");
		dataTableMod1.setVar("pbase");
		dataTableMod1.setValue(lstbase);
//		dataTableMod1.setSelectionMode("single");
//		dataTableMod1.setRows(10);	
		dataTableMod1.setRowHover(true);
		
		
//		ValueExpression rk =  context.getApplication().getExpressionFactory().createValueExpression("#{pbase.description}",String.class);
//		dataTableMod1.setValueExpression("rowKey", rk);
				
		
		System.out.println("holaasss" + dataTableMod1.getRowKey());
		
		

		}
}
	

