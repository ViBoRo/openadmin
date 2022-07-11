package openadmin.widgets.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import openDao.action.ContextAction;
import openDao.model.Base;
import openDao.util.messages.MessagesTypes;
import openadmin.model.ActionViewRole;
import openadmin.model.EntityAdm;
import openadmin.model.MenuItem;
import openadmin.model.Program;
import openadmin.widgets.jsf.component.TplMenu;
import openadmin.widgets.jsf.component.TplSimpleComponent;
import openadmin.widgets.jsf.component.TplToolbar;
import openadmin.widgets.jsf.view.InterceptorActionView;
import openadmin.widgets.jsf.view.InterceptorViewFacade;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;





	/**
	 *  Class that contains the methods called from the mainpage toolbar menu for genereting the program specific menus
	 *	@version  1.1
	 *  Created  18-03-2009
	 *  LastModified  05-04-2022
	 *  @author Alfred Oliver
	 *  @author Vicent Borja
	*/
@Named("main")
@SessionScoped
public class MainHome implements Serializable{
	
	@Inject
	private ContextAction ctx;
	
	@Getter @Setter
	private MenuModel toolbarModel = new DefaultMenuModel();
	
	/** Field that contain the left menu*/
	@Getter @Setter
	private MenuModel panelModel = new DefaultMenuModel();
	
	/** Field that contain the application name*/
	@Setter
	private HtmlOutputText program;
	
	/** Field that contain the original application name*/
	private String programName;
	
	/** Field that contain the entity select*/
	private EntityAdm entity;
	
	private String entityName;
	
	private InterceptorViewFacade view;

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6412868984425619677L;
	
	@PostConstruct
	public void init() {
		if(this.entity == null) {
			this.entity = ctx.getListEntity().get(0);
			this.entityName = entity.getDescription();
			this.programName = entityName;
		}
		
		loadToolbarModel();
		
    }
	/**
	 * <desc>class that execute action or load the toolbar</desc>
	 * @author 			Alfred Oliver
	 * @author 			Vicent Borja
	 */ 
	public void loadToolbarModel() {
		
		//Create item logout.
		TplToolbar toolbar = new TplToolbar();
		
		String[] file = new String[] {MessagesTypes.msgMain("home_menu_logout"), "#{ctx.logout}", "pi pi-sign-out"};
		toolbar.fileMenu(MessagesTypes.msgMain("home_menu_file"), file);
		
		//create aplication menu
		toolbar.aplicationsMenu(MessagesTypes.msgMain("home_menu_application"), ctx.getPrograms(), entity);
		
		//create entity menu
		toolbar.entitiesMenu(MessagesTypes.msgMain("home_menu_entity"), ctx.getListEntity());
		
		setToolbarModel(toolbar.getModel());
		
		
	}
	
	public void onItemClick(ActionEvent event) {
		
		
//		Object obj = event.getSource();
//		
//		DefaultMenuItem item = (DefaultMenuItem)obj;
		
		MenuActionEvent j = (MenuActionEvent) event; //cast para obtener las propiedades 

        DefaultMenuItem itm = (DefaultMenuItem) j.getMenuItem();
		       
		System.out.println("CLICK: " + itm.getParams().get("id").get(0));
		
		
		 if( itm.getParams().get("type").get(0) == "toolbar"){
		   if( itm != null ){
			        
		      //Change path
			  program.setValue( itm.getValue().toString());
			  programName = itm.getValue().toString();
		      entityName = itm.getValue().toString();
		     
		      //Load menu item
		      loadMenuModel(itm.getParams().get("id").get(0));
		     
		   }
		  }else if( itm.getParams().get("type").get(0) == "panel" ){	
				
			   	
			  System.out.println("------------Cargar View------------------");
			 try {
				System.out.println("he arribat aci");
				loadView(itm.getParams().get("id").get(0));
				
				program.setValue( entityName + "_" + itm.getValue().toString());
			    programName = entityName + "_" + itm.getValue().toString();
			   
			} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//catch
			  
			}//else if
		 
		  }//
		    
			
		  
		
		
		
		
		
//		MenuActionEvent j = (MenuActionEvent) event; //cast para obtener las propiedades 
//
//        DefaultMenuItem itm = (DefaultMenuItem) j.getMenuItem(); //el menu item al que se dio clic
//
//        Map<String, List<String>> params = itm.getParams(); //parametros del item
//        
//		System.out.println("id: "+  params.get("Id").get(0) + " item: " + params.get("Description").get(0));
		
		
	
	
	public void loadMenuModel(String pKey) {
		
		if (pKey == null) return;
		
		if(this.panelModel != null) {
			
			panelModel.getElements().clear();
			
		}
		
		//Load of menu items
		Set<MenuItem> listMenuItem = new TreeSet<MenuItem>();
		ActionViewRole actionViewRole = new ActionViewRole();
		System.out.println(pKey);
		System.out.println(ctx.getLoadMenuItems(pKey));
		for (Base b : ctx.getLoadMenuItems(pKey)) {
			
			
			actionViewRole = (ActionViewRole)b;
			listMenuItem.add(actionViewRole.getMenuItem());
			System.out.println("MenuItem CARGADO: " + pKey.toString() + " " + actionViewRole.getMenuItem().getDescription());
			
		}
		
		//choose if is parent or child
		for (MenuItem vr: listMenuItem){
					
					
			System.out.println("MENU ITEM NODE: " + vr.getTypenode());
			
			//MenuItem solt
			if (vr.getTypenode().equals("c") && vr.getParent() == null ){
				
				//Calls the method loadChild
				panelModel.getElements().add(TplMenu.loadChild(vr, (int) actionViewRole.getRole().getId()));
						
			}
			
			//MenuItem pare
			else if (vr.getTypenode().equals("p") && vr.getParent() == null ) {
										
				//Calls the method loadParent
				System.out.println("EL PARENT CARGADO: " + vr.getDescription());
				panelModel.getElements().add(TplMenu.loadParent(vr, listMenuItem,(int) actionViewRole.getRole().getId()));
						
			}
							
		}
	}
	
	/**
	 * <desc>class that execute action or load the view</desc>
	 * @author 			Alfred Oliver
	 * @author 			Vicent Borja
	 * @param pKey    	id menuItem and id role 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws ClassNotFoundException 
	 */ 
	private void loadView(String pKey) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{ 
		
		if (pKey == null) return;
		
		//Find all Action view role
		List<Base> loadActionView = ctx.getLoadActionsView(pKey);
		System.out.println("Elactionview" + loadActionView.get(0).getDescription());
		InterceptorActionView interceptor = new InterceptorActionView();
		ctx.removeViewAll();
		interceptor.actionView(loadActionView, ctx, 1);
		
		
	}
	
	public HtmlOutputText getProgram(){
		
	
		program = TplSimpleComponent.HtmlOutputText01(MessagesTypes.msgMain("home_menu_program") + programName); 
		
		return program;
	}

	
	

}
