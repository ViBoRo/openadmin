package openadmin.widgets.jsf.view;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.menubutton.MenuButton;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuItem;

import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import openDao.action.ContextAction;
import openDao.action.OtherActionFacada;
import openDao.model.Base;
import openDao.util.messages.MessagesTypes;
import openDao.util.messages.WebMessages;
import openDao.util.reflection.CreateInstance;
import openDao.util.reflection.ReflectionField;
import openadmin.util.configuration.SerialClone;
import openadmin.util.validator.WebValidator;
import openadmin.widgets.jsf.component.TplModalPanel;
import openadmin.widgets.jsf.component.TplModalPanelParent;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
//
//import org.ajax4jsf.component.html.HtmlAjaxCommandButton;
//import org.ajax4jsf.component.html.HtmlAjaxOutputPanel;
//import org.ajax4jsf.component.html.HtmlAjaxSupport;
//import org.richfaces.component.html.HtmlMenuItem;
//
//import openadmin.dao.Base;
//import openadmin.util.SerialClone;
//import openadmin.util.messages.MessagesTypes;
//import openadmin.util.messages.WebMessages;
//import openadmin.util.reflection.CreateInstance;
//import openadmin.util.reflection.ReflectionField;
//import openadmin.util.validator.WebValidator;
//import openadmin.widgets.jsf.component.TplModalPanel;
//import openadmin.widgets.jsf.component.TplModalPanelParent;
import openadmin.widgets.jsf.view.InterceptorActionView;




/**
 *  Class that contains the definition of the logic for executing actions called from the UI
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  05-04-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class ObjectAction implements Serializable, ObjectActionFacade{
	
	private static final long serialVersionUID = 19091001L;
	
	@Inject
	private ContextAction ctx;
	
	private Base base;
	
	//To edit
	private Base objOriginal;
	
	private List<Base> lstbase;
	
	private String metodo;
	
	/**-----------------------------------------------------
	  * Operation CRUD Data base
	  */ 
	public void operation(ActionEvent event) {
		
		Object obj = event.getSource();
		
	 	//Event of toolbar
	    if( obj instanceof CommandButton ){
	    	
	    	CommandButton command = (CommandButton)obj;
	    	
	    	if( command != null ){
	    		
	    		//Closed Modal Panel
	    		TplModalPanelParent panel = new TplModalPanelParent();
				panel.closedModalPanel("cmdmpyesno");
				command.setUpdate("idform");
				PrimeFaces.current().ajax().update("idform");
	    		//Operation
	    		if (command.getId().subSequence(0, 5).equals("idYes")){			
	    			
	    			// -------------- Operation New -------------------
	    			if (command.getId().subSequence(5, 8).equals("new")){	
	    				
	    				System.out.println("estic fent new1");
	    				if (WebValidator.execute(base)) return;
	    				
	    				System.out.println("estic fent new2: " + this.base.getId());
	    				
	    				if (this.base.getId() != null ) {
	    					
	    					WebMessages.messageError("exist_find_description");
	    					
	    					return;
	    					
	    				}
	    				System.out.println("estic fent new3");		
	    				ctx.getConnDefault().findObjectDescription(base);
	    				if (ctx.getConnDefault().isResultOperation()) {
	    						
	    					WebMessages.messageError("exist_find_description");

	    					return;
	    						
	    				}
	    				ctx.getConnDefault().begin();
	    				ctx.getConnDefault().persistObject(base);
	    				
	    				
	    			}
				
	    			// -------------- Operation Edit -------------------
	    			else if (command.getId().subSequence(5, 9).equals("edit")){	
	    				
	    				
	    				if (WebValidator.execute(base)) return;
	    				
	    				
	    				
	    				if (this.base.getId() == null) {
	    					
	    					
	    					WebMessages.messageError("noexist_find_pk");
	    					
	    					return;
	    					
	    				}
	    				
	    				ctx.getConnDefault().begin();
//	    				ctx.getConnDefault().updateObject(objOriginal, base);
	    				ctx.getConnDefault().updateObject(base);
	    			}
	    			
	    			// -------------- Operation Delete -------------------
	    			else if (command.getId().subSequence(5, 11).equals("delete")){	
	    				
	    				if (this.base.getId() == null) {
	    						    					
	    					
	    					WebMessages.messageError("noexist_find_pk");
	    					
	    					return;
	    					
	    				}
	    				
	    				ctx.getConnDefault().begin();
	    				ctx.getConnDefault().removeObject(base);
	    			}
	    			
	    			ctx.getConnDefault().commit();
 			
					this.base = (Base) CreateInstance.instanceObject(this.base.getClass().getCanonicalName());
	    			
					command.setUpdate("idform:idview");
	    			
					if (ctx.getConnDefault().isResultOperation())
					
						WebMessages.messageInfo("operation_" + command.getId().substring(5) + "_correct");
	    			
	    		}
	    		
	    		command.setUpdate("idform");
	    		
	    	}
	    	
	    }
	}
//	
//	//Operation New
	public void _new() {
		
 		TplModalPanel panel = new TplModalPanel();
		panel.modalPanelYesNo("new", "#{ctx.getView("+ ctx.sizeView() + ").operation}");
		PrimeFaces.current().ajax().update("idform");
	}
//
	//Operation Edit
	public void _edit() {
		
		TplModalPanel panel = new TplModalPanel();
		panel.modalPanelYesNo("edit", "#{ctx.getView("+ ctx.sizeView() + ").operation}");
		PrimeFaces.current().ajax().update("idform");
	
	}
//	
	//Operation Delete
	public void _delete() {
				
		TplModalPanel panel = new TplModalPanel();
		panel.modalPanelYesNo("delete", "#{ctx.getView("+ ctx.sizeView() + ").operation}");
		PrimeFaces.current().ajax().update("idform");
			
	}
	
	/**-----------------------------------------------------
	  * End Operation CRUD Data base
	  */  
			
	
//	public void otraAccion(Base pBase, String pAccion){
//				
//		String pack = base.getClass().getPackage().getName();
//   		int j=pack.lastIndexOf(".");
//   		pack=pack.substring(j+1, pack.length());
//   		Object objAction = CreateInstance.instanceObject("openadmin.action." +
//   				pack + "." +
//   				base.getClass().getSimpleName() + "Action");
//   		
//   		OtherActionFacada action = (OtherActionFacada) objAction;
//   	   		
//   		action.execute(pAccion, pBase, ctx);		
//		
//	}
//	
	public void otherAction(ActionEvent event){
		
//		PrimeFaces.current().ajax().update("idform");
//		Object obj = event.getSource();
		
		MenuActionEvent ae = (MenuActionEvent) event; //cast para obtener las propiedades 

        DefaultMenuItem itm = (DefaultMenuItem) ae.getMenuItem();
		       
		System.out.println("CLICK: " + itm.getParams().get("id").get(0));
	
		if( itm instanceof DefaultMenuItem){
			
			
			System.out.println("CUSTOMACTIONCALLED2" + itm.getParams().get("description"));
	       	if( itm != null ){
	       		
	       		String pack = base.getClass().getPackage().getName();
	       		int j=pack.lastIndexOf(".");
	       		pack=pack.substring(j+1, pack.length());
	       		//System.out.println("Package " + pack);	       		
	    		
	       		Object objAction = CreateInstance.instanceObject("openadmin.action." +
	       				pack + "." +
	       				itm.getParams().get("description").get(0) + "Action");
	       		
	       		OtherActionFacada action = (OtherActionFacada) objAction;
	       	
	       		action.execute(itm.getParams().get("id").get(0), base, ctx);

//	       		item.setReRender("idform");
	       	}
		}
		
//		else if( obj instanceof CommandButton ){
//		    	
//		    	CommandButton command = (CommandButton)obj;
//		    	
//		    	if( command != null ){
//		    		
//		    		String pack = base.getClass().getPackage().getName();
//		       		int j=pack.lastIndexOf(".");
//		       		pack=pack.substring(j+1, pack.length());
//		       		
//		       		Object objAction = CreateInstance.instanceObject("openadmin.action." +
//		       				pack + "." +
//		       				base.getClass().getSimpleName() + "Action");
//		       		
//		       		OtherActionFacada action = (OtherActionFacada) objAction;		       			     
//		       		
//		       		action.execute(command.getTitle(), base, ctx);
//		       		
//		       		//command.setReRender("idform");
//		    		
//		    	}
//			
//		}else if( obj instanceof MenuButton ){
//	    	
//			MenuItem mb = (MenuItem)obj;
//			System.out.println("CUSTOMACTIONCALLED2" + mb.getParams().get("description"));
//
//	    	if( mb != null ){
//	    		
//	    		String pack = base.getClass().getPackage().getName();
//	       		int j=pack.lastIndexOf(".");
//	       		pack=pack.substring(j+1, pack.length());
//	       		
//	       		Object objAction = CreateInstance.instanceObject("openadmin.action." +
//	       				pack + "." +
//	       				base.getClass().getSimpleName() + "Action");
//	       		
//	       		OtherActionFacada action = (OtherActionFacada) objAction;		       			     
//	       		
//	       		action.execute(mb.getTitle(), base, ctx);
//	       		
//	       		//command.setReRender("idform");
//	    		
//	    	}
//		
//	}
		
//		else if( obj instanceof HtmlAjaxSupport ){						
//			
//			HtmlAjaxSupport command = (HtmlAjaxSupport)obj;
//			
//			if( command != null ){
//	    		
//	    		String pack = base.getClass().getPackage().getName();
//	       		int j=pack.lastIndexOf(".");
//	       		pack=pack.substring(j+1, pack.length());
//	       		
//	       		Object objAction = CreateInstance.instanceObject("openadmin.action." +
//	       				pack + "." +
//	       				base.getClass().getSimpleName() + "Action");
//	       		
//	       		OtherActionFacada action = (OtherActionFacada) objAction;
//	       	
//	       		action.execute(command.getId(), base, ctx);
//	       		
//	       		command.setReRender("idform");
//	    		
//	    	}
//		}
		
	}
	
	
	public void _search() {
						
		List<Base> lstbaseNew = new ArrayList<Base>();
		
		lstbaseNew = ctx.getConnDefault().findObjects(base);
		
		lstbase = SerialClone.clone(lstbaseNew);
		
		if (lstbase.size() == 0 ) {
			
			WebMessages.messageError("noexist_find_pk");
			
			return;
			
		}
		
		TplModalPanel panel = new TplModalPanel();
		
		panel.modalPanel01("cerca", lstbase, "default", 
							"#{ctx.getView("+ ctx.sizeView() + ").setBase(pbase)}");	
	
		panel.openModalPanel("idform:mpsearchall");
		
		PrimeFaces.current().ajax().update("idform");
		
	}
	
	public void searchAll(ActionEvent event) {
		
		Object obj = event.getSource();
		
	 	//Event of toolbar
	    if( obj instanceof CommandButton ){
	    	
	    	CommandButton command = (CommandButton)obj;
	    	
	    	if( command != null ){
	    	
	    		System.out.println("Search action: " + base.getDescription());
	    		
	    		Base object = (Base) CreateInstance.instanceObject(command.getTitle().replace("_", "."));
	    		
	    		object.setDescription("%");
	    		
	    		List<Base> lstbase = new ArrayList<Base>();
	    		
	    		lstbase = ctx.getConnDefault().findObjects(object);
	    		
	    		if (lstbase.size() == 0 ) {
	    			
	    			WebMessages.messageError("noexist_find_pk");
	    			
	    			return;
	    			
	    		}
	    		
	    		TplModalPanel panel = new TplModalPanel();
	    
	    		panel.modalPanel01(MessagesTypes.msgDao(object.getClass().getSimpleName()), lstbase,   "default", 
						"#{ctx.getView("+ ctx.sizeView() + ").getBase().set"+ object.getClass().getSimpleName() +"(pbase)}");	
	    		
	    		panel.openModalPanel("idform:mpsearchall");
	    	}	
	    }
		
	}
	
	public void searchAll2(ActionEvent event) {
		
		Object obj = event.getSource();
		
	 	//Event of toolbar
	    if( obj instanceof CommandButton ){
	    	
	    	CommandButton command = (CommandButton)obj;
	    	
	    	if( command != null ){
	    	
	    		System.out.println("Search action: " + base.getDescription());
	    		
	    		Base object = (Base) CreateInstance.instanceObject(command.getTitle().replace("_", "."));
	    		
	    		object.setDescription("%");
	    		
	    		List<Base> lstbase = new ArrayList<Base>();
	    		
	    		lstbase = ctx.getConnDefault().findObjects(object);
	    		
	    		if (lstbase.size() == 0 ) {
	    			
	    			WebMessages.messageError("noexist_find_pk");
	    			
	    			return;
	    			
	    		}
	    		
	    		String metode = command.getId().substring(0,1).toUpperCase() + command.getId().substring(1);
	    			    		
	    		System.out.println("idfet: " + metode);
	    		
	    		TplModalPanel panel = new TplModalPanel();
	    
	    		panel.modalPanel01(MessagesTypes.msgDao(object.getClass().getSimpleName()), lstbase,   "default", 
						"#{ctx.getView("+ ctx.sizeView() + ").getBase().set"+ metode +"(pbase)}");	
	
	    		panel.openModalPanel("idform:mpsearchall");
	    	}	
	    }
		
	}
	
	public void searchSelect(ActionEvent event) {
		
		Object obj = event.getSource();
		
	 	//Event of toolbar
	    if( obj instanceof CommandButton ){
	    	
	    	CommandButton command = (CommandButton)obj;
	    	
	    	if( command != null ){
	    		
	    		Base object = (Base) CreateInstance.instanceObject(command.getTitle().replace("_", "."));
	    		
	    		Base ba = ReflectionField.searchSelect(object, base);	    		
	    		
	    		if (ba == null){	    			
	    		
	    			object.setDescription("%");
	    		
	    		} else{
	    		
	    			object = ba;
	    		
	    		}
	    		
	    		List<Base> lstbase = new ArrayList<Base>();
	    		
	    		lstbase = ctx.getConnDefault().findObjects(object);
	    		
	    		if (lstbase.size() == 0 ) {
	    			
	    			WebMessages.messageError("noexist_find_pk");
	    			
	    			return;
	    			
	    		}
	    		
	    		TplModalPanel panel = new TplModalPanel();
	    		    	
	    		panel.modalPanel01(MessagesTypes.msgDao(object.getClass().getSimpleName()), lstbase,   "default", 
						"#{ctx.getView("+ ctx.sizeView() + ").getBase().set"+ object.getClass().getSimpleName() +"(pbase)}");	
	
	    		panel.openModalPanel("idform:mpsearchall");
	    	}	
	    }

	}
	
	public void editObject(ActionEvent event) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		
		Object obj = event.getSource();
		
	 	//Event of toolbar
	    if( obj instanceof CommandButton ){
	    	
	    	CommandButton command = (CommandButton)obj;
	    	
	    	if( command != null ){
	    			    		
	    		metodo = command.getId().substring(3, 4).toUpperCase() + command.getId().substring(4).trim();
	    		
	    		if ((ctx.getLoadActionsView(("ida" + command.getTitle().substring(3)).toLowerCase())).size() == 0) return;

	    		List<Base> loadActionView = ctx.getLoadActionsView(("ida" + command.getTitle().substring(3)).toLowerCase());
	    		
	    		InterceptorActionView interceptor = new InterceptorActionView();
	    		interceptor.actionView(loadActionView, ctx, ctx.sizeView() + 1);
	    			    			    	
	    	}
	    }
	}
	
	//Copy Object
	public void _copy() {
				
		Base _obj = ctx.getView(ctx.sizeView()).getBase();
		
			
		if (  null !=_obj && ctx.sizeView() > 1){
						
			ReflectionField refl = new ReflectionField();
				
			refl.copyObject(_obj, ctx.getView(ctx.sizeView() - 1).getBase(), ctx.getView(ctx.sizeView() - 1).getMetodo());
				
			ctx.removeView();
				
			FacesContext _context = FacesContext.getCurrentInstance();
				
			CommandButton outView = (CommandButton)_context.getViewRoot().findComponent("idform:idview");
				
			outView.getChildren().clear();
				
			outView.getChildren().add(ctx.getView(ctx.sizeView()).getOutPanel());
				
		}
			
	}
//	
//	//Closed modal panel
	public void closedModalPanel(ActionEvent event){
		
		Object obj = event.getSource();
		TplModalPanelParent panel = new TplModalPanelParent();
		
//	    if( obj instanceof CommandButton ){
	    	
	    	CommandButton command = (CommandButton)obj;
	    	
	    	if( command != null ){
	    		
	    		panel.closedModalPanel(command.getId());
	    		command.setUpdate("idform");
	    		PrimeFaces.current().ajax().update("idform");
	    		
	    	}
//	    }
//	    
//	    else if( obj instanceof HtmlAjaxSupport ){
//	    	
//	    	HtmlAjaxSupport command = (HtmlAjaxSupport)obj;
//	    	
//	    	if( command != null ){
//	    		
//	    		panel.closedModalPanel(command.getId());
//	    		command.setReRender("idform");
//	    	}
//	    }
	    
		
	}
//	
//	//Ejecuta otra acción
//	public void closedModalPanel2(ActionEvent event){
//		
//		Object obj = event.getSource();
//		TplModalPanelParent panel = new TplModalPanelParent();
//		
//	    if( obj instanceof HtmlAjaxCommandButton ){
//	    	
//	    	HtmlAjaxCommandButton command = (HtmlAjaxCommandButton)obj;
//	    	
//	    	if( command != null ){
//	    		
//	    		panel.closedModalPanel(command.getId());
//	    		
//	    		String pack = base.getClass().getPackage().getName();
//	       		int j=pack.lastIndexOf(".");
//	       		pack=pack.substring(j+1, pack.length());
//	       		//System.out.println("Package " + pack);
//	       		Object objAction = CreateInstance.instanceObject("openadmin.action." +
//	       				pack + "." +
//	       				base.getClass().getSimpleName() + "Action");
//	       		
//	       		OtherActionFacada action = (OtherActionFacada) objAction;
//	       	
//	       		action.execute(command.getImage(), base, ctx);
//	    		
//	    		command.setReRender("idform");
//	    	}
//	    }
//	    
//	    else if( obj instanceof HtmlAjaxSupport ){
//	    	
//	    	HtmlAjaxSupport command = (HtmlAjaxSupport)obj;
//	    	
//	    	if( command != null ){
//	    		
//	    		panel.closedModalPanel(command.getId());
//	    		command.setReRender("idform");
//	    	}
//	    }
//	    
//		
//	}
//	
	public void _limpia() {
		
		this.base = (Base) CreateInstance.instanceObject(this.base.getClass().getCanonicalName());
	}
	
	
	
	/**-----------------------------------------------------
	 * ---------------- getters i setters --------------------
	 * -----------------------------------------------------
	 */
	
	public Base getBase() {
		
		return this.base;
	
	}

	public void setBase(Base pBase) {
		
		objOriginal = SerialClone.clone(pBase);

		this.base =  pBase;
	
	} 
	
	public ContextAction getCtx() {
		return ctx;
	}

	public void setCtx(ContextAction ctx) {
		this.ctx = ctx;
	}

	public String getMetodo() {
		return metodo;
	}

	


}
