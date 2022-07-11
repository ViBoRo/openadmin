package openadmin.widgets.jsf.component;

import jakarta.el.MethodExpression;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.MethodExpressionActionListener;
import jakarta.inject.Named;

import openDao.util.messages.MessagesTypes;
import openadmin.model.Action;
import openadmin.model.EntityAdm;
import openadmin.model.Program;
import openadmin.model.Role;
import openadmin.util.configuration.EntityRole;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.menubutton.MenuButton;
import org.primefaces.component.toolbar.Toolbar;
import org.primefaces.component.toolbar.ToolbarGroup;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *  Class that defines the toolbar component 
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  12-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/

@Named
public class TplToolbar implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6412868984425619677L;
	private MenuModel model;
	

	
	public  TplToolbar(){
		
		 model = new DefaultMenuModel();
		
	}

	
	public void fileMenu(String head, String[] lst) {
		
		   DefaultSubMenu firstSubmenu = DefaultSubMenu.builder()
	                .label(head)
	                .icon("pi pi-home")
	                .build();

	        DefaultMenuItem item = DefaultMenuItem.builder()
	                .value(lst[0])
	                .url("http://www.primefaces.org")
	                .icon(lst[2])
	                .build();
	        firstSubmenu.getElements().add(item);
	        model.getElements().add(firstSubmenu);
	        
	}
	
	public void aplicationsMenu(String head, List<EntityRole> listEp, EntityAdm entity) {
			
		DefaultSubMenu aplicationmenu = DefaultSubMenu.builder()
                .label(head)
                .icon("pi pi-desktop")
                .build();
		
		for (EntityRole er: listEp) {
			
			for (Role ro: er.getRole()){
				
				Program pr = ro.getProgram();
				
				 DefaultMenuItem item = new DefaultMenuItem();
				 
			     item.setValue(pr.getDescription());
			     
			     Map<String, List<String>> params = new HashMap<String, List<String>>();
			     params.put("id", Arrays.asList(new String[]{"idi" +er.getEntity().getId() + "_" + (int) pr.getId()}));
			     params.put("description", Arrays.asList(new String[]{pr.getDescription()}));
			     params.put("type", Arrays.asList(new String[]{"toolbar"}));
			     System.out.println("Programa: " + pr.getId() + pr.getDescription() + " " + "idi" + er.getEntity().getId() + "_" + (int) pr.getId());
			     //item.setId(pr.getId().toString())
			     item.setParams(params);
			     item.setCommand("#{main.onItemClick}");
			     item.setUpdate("idmainmenuview");
			     
			     aplicationmenu.getElements().add(item);
			}
		        
		}
		
		model.getElements().add(aplicationmenu);
	}
	
	public void entitiesMenu(String head, List<EntityAdm> listEp) {
			
		DefaultSubMenu entitymenu = DefaultSubMenu.builder()
                .label(head)
                .icon("pi pi-briefcase")
                .build();
		
		for (EntityAdm en: listEp) {
			
			 DefaultMenuItem item = DefaultMenuItem.builder()
		                .value(en.getDescription())
		                .url("http://www.primefaces.org")
		                .build();
		        entitymenu.getElements().add(item);
		}
		model.getElements().add(entitymenu);
	}
	
	public static Toolbar createToolActions(Integer view, List<Action> actions){
			
			Application app = FacesContext.getCurrentInstance().getApplication();
			
			Toolbar toolbar = new Toolbar();
			//toolbar.setStyleClass("toolBarAction");
			
			ToolbarGroup toolbarGroup =  new ToolbarGroup();
			
			ToolbarGroup toolbarAccions =  new ToolbarGroup();
			toolbarAccions.setAlign("right");
			
			//Accions
			MenuButton accions = (MenuButton) app.createComponent(MenuButton.COMPONENT_TYPE);
			MenuModel accionsmodel = new DefaultMenuModel();
			accions.setValue(MessagesTypes.msgOperationWeb("action"));
			
			//Imprimeix
			MenuButton imprimeix = (MenuButton) app.createComponent(MenuButton.COMPONENT_TYPE);
			MenuModel printmodel = new DefaultMenuModel();
			imprimeix.setValue(MessagesTypes.msgOperationWeb("print"));
		
			for (Action ac: actions){	
				
				DefaultMenuItem item = new DefaultMenuItem();
				System.out.println("la action a rellenar" + ac.getDescription());
				//Other Actions
				if (ac.getDescription().indexOf(":") > 0){
					
					

					Map<String, List<String>> params = new HashMap<String, List<String>>();
				    params.put("id", Arrays.asList(new String[]{ac.getDescription().substring(ac.getDescription().indexOf(":")+1).trim()}));
				    
				    String actionName = ac.getDescription().substring(ac.getDescription().indexOf(":")+1).trim();
				    params.put("description", Arrays.asList(new String[]{actionName.substring(0, 1).toUpperCase() + actionName.substring(1)}));
				    //params.put("type", Arrays.asList(new String[]{"panel"}));
				    
				    item.setParams(params);
					//item.setId(ac.getDescription().substring(ac.getDescription().indexOf(":")+1).trim());
					item.setValue( MessagesTypes.msgOperationWeb(ac.getDescription().substring(ac.getDescription().indexOf(":")+1).trim()));
					//item.setIcon(ac.getIcon());
					item.setCommand("#{ctx.getView("+ view + ").otherAction}");
					item.setUpdate("idmainmenuview");
					
					System.out.println("A");
						
				} else {
						
					item.setValue( MessagesTypes.msgOperationWeb(ac.getDescription().substring(ac.getDescription().indexOf("_")+1).trim()));
					item.setIcon(ac.getIcon());
					item.setCommand("#{ctx.getView("+ view + ").");
					System.out.println("B");
				}
					
				if (ac.getGroupid().equals((byte) 1)) { 

					accionsmodel.getElements().add(item);
					accions.setModel(accionsmodel);
					System.out.println("C");
					
					
				}else if (ac.getGroupid().equals((byte) 2)) {
					printmodel.getElements().add(item);
					imprimeix.setModel(printmodel);
					System.out.println("D");
				}else{
					System.out.println("F");
					CommandButton other = (CommandButton) app.createComponent(CommandButton.COMPONENT_TYPE);
					other.setValue( MessagesTypes.msgOperationWeb(ac.getDescription().substring(ac.getDescription().indexOf("_")+1).trim()));
					//item.setIcon(ac.getIcon());
					
					
					other.setActionExpression(app.getExpressionFactory().createMethodExpression
							(FacesContext.getCurrentInstance().getELContext(), 
								item.getCommand() + ac.getDescription().substring(ac.getDescription().indexOf("_")).trim() +"}", String.class ,new Class[]{}));
					System.out.println(item.getCommand() + ac.getDescription().substring(ac.getDescription().indexOf("_")).trim());
					toolbarGroup.getChildren().add(other);
					toolbar.getChildren().add(toolbarGroup);
					System.out.println("F");
					
				}
				
				
			}
			
			System.out.println("JEEJJE" + accions.getChildCount());
			if (accionsmodel.getElements().size() > 0) {
				toolbarAccions.getChildren().add(accions);
				toolbar.getChildren().add(toolbarAccions);
				System.out.println("X");
				}
			
			if (imprimeix.getChildCount() > 0) {
			toolbar.getChildren().add(imprimeix);
			toolbar.getChildren().add(toolbarGroup);
			System.out.println("Y");
			}
			
			if (view > 1){
				
				
				ToolbarGroup salirgroup =  new ToolbarGroup();
				
				CommandButton salir = new CommandButton();
				
				salir.setValue(MessagesTypes.msgOperation("exit"));
				salir.setIcon("../resources/icon /salir.png");
				salir.setActionExpression(app.getExpressionFactory().createMethodExpression
						(FacesContext.getCurrentInstance().getELContext(), 
						"#{ctx.getView("+ view + ")._copy}", String.class ,new Class[]{}));
				

				CommandButton limpiar = new CommandButton();
				limpiar.setValue(MessagesTypes.msgOperation("limpia"));
				limpiar.setIcon("../resources/icon/limpia.png");
				
			
				
				salir.getChildren().add(salir);
				salir.getChildren().add(limpiar);
				salirgroup.getChildren().add(salir);	
				toolbar.getChildren().add(salirgroup);
				
				System.out.println("Z");
			}
			
			return toolbar;
		}



    public MenuModel getModel() { 
    	
        return model;
        
    }
}
