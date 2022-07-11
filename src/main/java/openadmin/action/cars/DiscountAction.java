package openadmin.action.cars;

import java.io.Serializable;

import org.primefaces.PrimeFaces;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.gmap.GMap;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import jakarta.faces.component.html.HtmlForm;
import jakarta.faces.context.FacesContext;
import openDao.action.ContextAction;
import openDao.action.OtherActionFacada;
import openDao.model.Base;
import openadmin.widgets.jsf.component.TplSimpleComponent;


/**
 *  The custom action definition for aplying a discount to the repair price
 *	@version  1.0
 *  @author Vicent Borja
*/
public class DiscountAction implements Serializable, OtherActionFacada{
	
	private static final long serialVersionUID = -6003532022249791176L;
	
	/**
	 * Method called when the action EL command is executed
	 * @param pAction Base object with the form information
	 * @param pBase Base object with the form information
	 * @param pCtx context information
	 */	
	public void execute(String pAction, Base pBase, ContextAction pCtx){
		

		
	}
	
}
