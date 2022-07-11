package openadmin.widgets.jsf.component;


import jakarta.inject.Named;
import openDao.util.messages.MessagesTypes;
import openadmin.model.MenuItem;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;


/**
 *  Class that defines the menu component 
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  13-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/

@Named
public class TplMenu implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6178959551819992537L;
	/**
	 * 
	 */
	
	private MenuModel model;
	

	
	public  TplMenu(){
		
		 model = new DefaultMenuModel();
		
	}

//	public void buildMenu(String head, List<String> listEp) {
//		
//		   DefaultSubMenu firstSubmenu = DefaultSubMenu.builder()
//	                .label(head)
//	                .build();
//		   
//		   for (String me: listEp) {
//				System.out.println("ELMENU: " + me);
//				 DefaultMenuItem item = DefaultMenuItem.builder()
//			                .value(me)
//			                .url("http://www.primefaces.org")
//			                .build();
//			        firstSubmenu.getElements().add(item);
//			}
//	        model.getElements().add(firstSubmenu);
//		
//	}
	
	public static DefaultMenuItem loadChild(MenuItem pMenuItem, int pRol) {
		 
		DefaultMenuItem menuitem = new DefaultMenuItem();
		
		//Name menu item
		menuitem.setValue(MessagesTypes.msgMain(pMenuItem.getDescription()));
		
		//menuItem.getChildren().add(TplSimpleComponent.tooltip01(language.getString(menuItemRole.getMenuItem().getDescription())));
		
		//Id menu item (id view and id role)
		Map<String, List<String>> params = new HashMap<String, List<String>>();
	    params.put("id", Arrays.asList(new String[]{"ida" + pMenuItem.getDescription()}));
	    params.put("description", Arrays.asList(new String[]{pMenuItem.getDescription()}));
	    params.put("type", Arrays.asList(new String[]{"panel"}));
	    
	    menuitem.setParams(params);
		menuitem.setCommand("#{main.onItemClick}");
		menuitem.setUpdate("idmainmenuview,idprogram");
		
		//Action items
		
		return menuitem;
		
	 }
	
	public static DefaultSubMenu loadParent(MenuItem pMenuItem, Set<MenuItem> lstMenuItem, int pRol){
		
			 
		 DefaultSubMenu submenu = new DefaultSubMenu();
		 
		 //Es monta submenu
		 submenu.setLabel( pMenuItem.getDescription());
		 
		 //Es comproba si te algo dins per a montar 
		 for (MenuItem vr: lstMenuItem ){
			 
			 //MenuItem dins de este pare
			 if (pMenuItem.equals(vr.getParent()) && vr.getTypenode().equals("c")){
				 
				//Calls the method loadChild
				 submenu.getElements().add(loadChild(vr, pRol));
				 
			 }	 
			 
			 //MenuItem pare dins de este pare
			 else if (pMenuItem.equals(vr.getParent()) && vr.getTypenode().equals("p")){
				System.out.println("PARE DINS DE PARE: " + vr.getDescription());
				//Calls again the method loadParent
				 submenu.getElements().add(loadParent(vr, lstMenuItem, pRol));
				 
			 }	 
			 
		 }
		 
		 
		 return submenu;
	 }


    public MenuModel getModel() { 
    	
        return model;
        
    }
}
