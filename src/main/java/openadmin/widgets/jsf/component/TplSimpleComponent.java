package openadmin.widgets.jsf.component;


import java.io.Serializable;
import java.text.NumberFormat;
//import javax.el.MethodExpression;
//import javax.faces.application.Application;
//import javax.faces.component.UIComponent;
//import javax.faces.component.html.HtmlInputTextarea;
//import javax.faces.component.html.HtmlOutputLabel;
//import javax.faces.component.html.HtmlOutputText;
//import javax.faces.component.html.HtmlPanelGrid;
//import javax.faces.component.html.HtmlSelectBooleanCheckbox;
//import javax.faces.context.FacesContext;
//import javax.faces.event.MethodExpressionActionListener;
//import org.ajax4jsf.component.html.HtmlAjaxCommandButton;
//import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
//import org.ajax4jsf.component.html.HtmlAjaxSupport;
//import org.richfaces.component.html.HtmlComponentControl;
//import org.richfaces.component.html.HtmlInputText;
//import org.richfaces.component.html.HtmlTab;
//import org.richfaces.component.html.HtmlToolTip;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;

import jakarta.el.MethodExpression;
import jakarta.faces.application.Application;
import jakarta.faces.component.html.HtmlOutputLabel;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGrid;
import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.MethodExpressionActionListener;

/**
 *  Class that defines the simple components
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  14-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class TplSimpleComponent implements Serializable{

	private static final long serialVersionUID = 16051001L;
	
	public static HtmlOutputLabel HtmlLabel01(String value){
		
		HtmlOutputLabel label = new HtmlOutputLabel();
		label.setValue(value);
		label.setStyleClass("output");
		
		return label;
	
	}
	
	public static HtmlOutputLabel HtmlLabel02(String value){
		
		HtmlOutputLabel label = new HtmlOutputLabel();
		label.setValue(value);
		label.setStyleClass("outputlabel");
		
		return label;
	
	}
	
	public static HtmlPanelGrid panelGrid(int column){
		
		HtmlPanelGrid panelData = new HtmlPanelGrid();
		panelData.setColumns(column);
		
		return panelData;
	}
	
	public static InputText HtmlInput01(int pLong, boolean readOnly, String value, Class<?> typeClass){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		InputText input = new InputText();
				
		input.setMaxlength(pLong);
		
		input.setSize(pLong + 3);
		
		input.setReadonly(readOnly);
		
		if (readOnly) input.setStyleClass("inputReadOnly");
		
		input.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
		
		return input;
	
	}
//	
//	public static HtmlInputText HtmlInput02(int pLong, boolean readOnly, String value, Class<?> typeClass){
//		
//		Application app = FacesContext.getCurrentInstance().getApplication();
//		
//		HtmlInputText input = new HtmlInputText();
//		
//		NumberFormat nf = NumberFormat.getInstance();				
//		
//		input.setMaxlength(pLong);
//		
//		input.setSize(pLong + 3);
//		
//		input.setReadonly(readOnly);
//		
//		if (readOnly) input.setStyleClass("inputReadOnly");
//		
//		input.setValueExpression("value", app.getExpressionFactory().createValueExpression(
//				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
//		
//		return input;
//	
//	}
//	
	public static InputTextarea textArea01(int pLong, String value, Class<?> typeClass){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		InputTextarea textArea = new InputTextarea();
		
		textArea.setCols(80);
		textArea.setRows(2);
		
		textArea.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
		
		return textArea;
		
	}
//	
//	public static HtmlInputTextarea textArea02(int pLong, boolean readOnly, String value, Class<?> typeClass){
//		
//		Application app = FacesContext.getCurrentInstance().getApplication();
//		
//		HtmlInputTextarea textArea = new HtmlInputTextarea();
//		
//		textArea.setReadonly(readOnly);
//		
//		if (readOnly) textArea.setStyleClass("inputReadOnly");
//		
//		textArea.setCols(80);
//		textArea.setRows(2);
//		
//		textArea.setValueExpression("value", app.getExpressionFactory().createValueExpression(
//				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
//		
//		return textArea;
//		
//	}
//	
	public static HtmlOutputText HtmlOutputText01(String value){
		
		HtmlOutputText outputText = new HtmlOutputText();
		outputText.setValue(value);
		return outputText;
	
	}
//	
	public static CommandButton commandFr1(String pValue, String pAction, String id, String icon){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		CommandButton command = new CommandButton();
		
		command.setId(id);
		
		//Action
		MethodExpression methodExpr =  app.getExpressionFactory().createMethodExpression
			(FacesContext.getCurrentInstance().getELContext(),  pAction, null ,new Class[]{ jakarta.faces.event.ActionEvent.class});
		 
		 //Listener
		command.addActionListener(new MethodExpressionActionListener(methodExpr));
		
		command.setStyleClass("button");
		command.setValue(pValue);
		command.setImage(icon);
		
		return command;
	}
//	
	public static CommandButton commandFr2(String pValue, String pAction, String pantalla, String id, String icon){
		
		System.out.println("Pantalla: " + pantalla);
		System.out.println("id: " + id);
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		CommandButton command = new CommandButton();
				
		command.setId(id.replace(".", "_"));
		command.setTitle(pantalla.replace(".", "_"));
		//Action
		MethodExpression methodExpr =  app.getExpressionFactory().createMethodExpression
			(FacesContext.getCurrentInstance().getELContext(),  pAction, null ,new Class[]{ jakarta.faces.event.ActionEvent.class});
		 
		 //Listener
		command.addActionListener(new MethodExpressionActionListener(methodExpr));
		
		//command.setOncomplete("Richfaces.showModalPanel('" + pOpenPanel + "')" );
		
		command.setStyleClass("button");
		command.setValue(pValue);
		command.setImage(icon);
		command.setUpdate("idform");
		
		return command;
	}
//	
//	
//	public static UIComponent commandLink(String action){
//		
//		HtmlAjaxCommandLink commandlink = new HtmlAjaxCommandLink();
//		
//		commandlink.setValue(action);
//		commandlink.setId("idlink");
//		
//		HtmlComponentControl control = new HtmlComponentControl();
//		control.setFor("idform:idmodalpanel1");
//		control.setAttachTo("idlink");
//		control.setOperation("show");
//		control.setEvent("onclick");
//		
//		commandlink.getChildren().add(control);
//		
//		return commandlink;
//	
//	}
//	
//	public static HtmlToolTip tooltip01(String pValue){
//		
//		HtmlToolTip tooltip = new HtmlToolTip();
//		
//		tooltip.setFollowMouse(true);
//		
//		tooltip.setValue(pValue);
//		
//		return tooltip;
//		
//	}
//	
//	public static HtmlTab tab (String label){
//		
//		HtmlTab tab = new HtmlTab();
//		tab.setId(label);
//		tab.setLabel(label);
//		
//		return tab;
//	}
//	
	public static HtmlSelectBooleanCheckbox checkbox (String value){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		HtmlSelectBooleanCheckbox check = new HtmlSelectBooleanCheckbox();
		
		check.setValueExpression("value", app.getExpressionFactory().createValueExpression(
		FacesContext.getCurrentInstance().getELContext(), value, boolean.class));
						
		return check;
	}
//	
//	public static HtmlAjaxSupport htmlAjaxSupport(String pAction, String id){
//		
//		FacesContext context = FacesContext.getCurrentInstance();
//		
//		HtmlAjaxSupport support = new HtmlAjaxSupport();
//		support.setEvent("onchange");
//		support.setId(id);
//		
//		//Action
//		MethodExpression methodExpr =  context.getApplication().getExpressionFactory().createMethodExpression
//		(context.getELContext(), pAction, null ,new Class[]{ javax.faces.event.ActionEvent.class});
//		 
//		 //Listener
//		support.addActionListener(new MethodExpressionActionListener(methodExpr));
//		
//		//support.setActionExpression(context.getApplication().getExpressionFactory().createMethodExpression
//		//					(context.getELContext(), 
//		//							pAction, null ,new Class[]{javax.faces.event.ActionEvent.class}));
//		 
//		//support.setReRender("idform:idoutview");
//		
//		return support;
//		
//	}

}




