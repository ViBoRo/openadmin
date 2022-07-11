package openadmin.widgets.jsf.component;

import java.io.Serializable;
import java.util.List;
//
//import javax.faces.component.UIComponent;
//import javax.faces.component.html.HtmlForm;
//import javax.faces.context.FacesContext;
//
//import org.ajax4jsf.component.html.HtmlAjaxSupport;
//import org.richfaces.component.html.HtmlModalPanel;

import org.primefaces.PrimeFaces.Ajax;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.dialog.DialogRenderer;
import org.primefaces.component.gmap.GMap;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.context.FacesContext;

/**
 *  Class that defines the modal panel component 
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  10-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class TplModalPanelParent implements Serializable{

	private static final long serialVersionUID = 26061001L;
	
	protected FacesContext context = FacesContext.getCurrentInstance();
	
	//Find form
	protected HtmlForm frm = (HtmlForm)context.getViewRoot().findComponent("idform");
	
	protected Dialog modalPanel;
	
	public void execute(String pHeader, String pMpId, Integer length){
		
		modalPanel = new Dialog();
		
		modalPanel.setId(pMpId);
		
		modalPanel.setMinWidth(length);
		
		//modalPanel.setMinHeight(50);
		
		//modalPanel.isRendered();
		
		modalPanel.setResizable(true);
		
		modalPanel.setVisible(true);
		
		
		//modalPanel.setShowWhenRendered(true);
		
        modalPanel.getFacets().put("header", TplSimpleComponent.HtmlOutputText01(pHeader));
		
 
    
	}
	
	//Closed modal panel
	public void closedModalPanel(String pid){
		
	    int numberChildren = frm.getChildCount();
	    		 
	    if (numberChildren > 0) {

	    		UIComponent component = null;
	    		
	    		List lstChildren = frm.getChildren();
	    		
	    		while (--numberChildren >= 0) {
	    		
	    			component = (UIComponent) lstChildren.get(numberChildren);
	    			
	    			System.out.println("ID Component |" + component.getId() + "| " + pid);
	    			
	    		    if (component.getId().equals(pid.substring(3))) {
	    		    	
	    		    	System.out.println("Tancat ID Component " + pid.substring(3));
	    		    	System.out.println("Borrant Children");
	    		    	lstChildren.remove(numberChildren);	
	    		    	continue;
	    		    }
	    		}
	   }  		
	}
	
	public void openModalPanel(String pNamePanel){
		
//		HtmlAjaxSupport support = new HtmlAjaxSupport();
//		support.setOncomplete("Richfaces.showModalPanel('" + pNamePanel + "')" );
//		support.setReRender("idform");
//		modalPanel.getChildren().add(support);
		
		System.out.println("ObrirModalPanel" + pNamePanel);
		
	}
	
}
