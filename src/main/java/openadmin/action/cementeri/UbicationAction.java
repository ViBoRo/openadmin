package openadmin.action.cementeri;

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
 *  The custom action definition for showing the niche ubication on a map
 *	@version  1.0
 *  @author Vicent Borja
*/
public class UbicationAction implements Serializable, OtherActionFacada{
	
	private static final long serialVersionUID = -6003532018249791176L;
	
	/**
	 * Method called when the action EL command is executed
	 * @param pAction Base object with the form information
	 * @param pBase Base object with the form information
	 * @param pCtx context information
	 */	
	public void execute(String pAction, Base pBase, ContextAction pCtx){
		
		openMap(pBase);
		
	}
	
	/**
	 * Method that shows a map inside a window using the PrimeFaces Gmap component
	 * @param pBase Base object with the form information
	 */	
	private void openMap(Base pBase) {
		
		System.out.println("OPEN MAP"+ pBase.getDescription());
		
		FacesContext context = FacesContext.getCurrentInstance();
		HtmlForm frm = (HtmlForm)context.getViewRoot().findComponent("idform1");
		
		MapModel model = new DefaultMapModel();
		
		GMap mapa = new GMap();
		Dialog dialog = new Dialog();
	
		dialog.setModal(true);
		dialog.setStyle("width:400px;height:400px");
		
		mapa.setModel(model);
		model.addOverlay(new Marker(new LatLng(39.17698005460142, -0.25853706631033263), pBase.getDescription()));
		mapa.setCenter("41.381542, 2.122893");
		mapa.setType("HYBRID");
		mapa.setStyle("width:400px;height:400px");
		mapa.setZoom(15);
		
		dialog.getChildren().add(mapa);
		
		dialog.setRendered(true);
		
		frm.getChildren().add(dialog);
		
		PrimeFaces.current().ajax().update("idform1");
		
	}
}
