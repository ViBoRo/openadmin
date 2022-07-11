package openDao.action;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

//import org.jboss.seam.annotations.In;
//import org.jboss.seam.annotations.Out;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import openDao.connection.ConnectionDao;
import openDao.model.Base;
import openDao.model.log.Log;
import openDao.model.log.LogOperationFacade;
import openDao.operation.DaoJpaHibernate;
import openDao.operation.DaoOperationFacade;
import openDao.util.messages.MessagesTypes;
//import openadmin.action.Integer;
//import openadmin.action.Integer;
//import openadmin.action.String;
//import openadmin.dao.operation.LogDao;
//import openadmin.dao.operation.LogOperationFacade;
//import openadmin.util.configuration.EntityRole;
//import openadmin.util.configuration.TypeEnvironment;
//import openadmin.util.configuration.TypeLanguages;
//import openadmin.util.firstcontrolload.FirstControlLoad;
//import openadmin.widgets.jsf.view.ViewFacade;
//import openadmin.dao.operation.LogOperationFacade;
import openadmin.model.Access;
import openadmin.model.ActionViewRole;
import openadmin.model.EntityAdm;
import openadmin.model.MenuItem;
import openadmin.model.Program;
import openadmin.model.Role;
import openadmin.model.User;
import openadmin.util.configuration.EntityRole;

import openadmin.util.data.InitDataLoad;
import openadmin.widgets.jsf.view.ViewFacade;

//import openDao.widgets.jsf.view.ViewFacade;
import java.util.Set;
//import org.jboss.seam.annotations.In;
//import org.jboss.seam.annotations.Out;



/**
 *  Class that defines the context bean where the acces control data related to the user is stored during session
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  27-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/

@Named("ctx")
@SessionScoped
public class ContextAction implements Serializable{

	private static final long serialVersionUID = 21100901L;

	/** Field that contain the connection*/
	private DaoOperationFacade connControl = null;
	
	/** Field that contain the connection with log*/
	private DaoOperationFacade connLog = null;
	
	/** Field that contain the connection with log*/
	private DaoOperationFacade connDefault = null;
	
//	/** Field that contain the connection territorio*/
//	private DaoOperationFacade connTerritorio = null;
//	
//	/** Field that contain the connection territorio*/
//	private DaoOperationFacade connTercer = null;
	
	/** Field that contain the menuItems*/
	private Map<String, List<Base>> menuItems = new HashMap<String, List<Base>>();
	
	/** Field that contain the actions of the view*/
	private Map<String, List<Base>> actionsViews = new HashMap<String, List<Base>>();

	private Set<EntityAdm> listEntity = new TreeSet<EntityAdm>();
	
	private  List<EntityRole> programs = new ArrayList<EntityRole>();
	
	private Map<Integer, ViewFacade> lstView = new HashMap<Integer, ViewFacade>();
	
	private EntityAdm entityDefault = null;

	
	@Getter @Setter
	private User user;
	
	@Getter @Setter
	private String username;
	
	@Getter @Setter 
	private String program;

	/** If is connect with database*/
	private boolean connected = false;
	
	/** Field that contain the Log 
	 * @throws ClassNotFoundException */
	//LogOperationFacade log = null;
			
	public boolean login () throws ClassNotFoundException  {
			
			boolean exist = false;
			
			//Database connection 
			if (!connected){
				
				//hi ha que modificar l'idioma del log per el de la configuracio manual, la connexió també  
				//MessagesTypes.changeMessageLog(TypeLanguages.es);
				MessagesTypes.changeLanguage("es");
						
				//Log connection
//				connLog = new DaoJpaHibernate(user, "log_post", null);									
//				log = new LogDao(connLog, "clientweb");
				
				
				
				//connection
				connLog = new DaoJpaHibernate 
						(new ConnectionDao().getEntityManagerDao("log_post"), "ca", this.username, "log");
//				
//				connControl = new DaoJpaHibernate 
//						(new ConnectionDao().getEntityManagerDao("openadmin"), "ca", (DaoJpaHibernate) connLog);
//				
				connControl = new DaoJpaHibernate 
						(new ConnectionDao().getEntityManagerDao("openadmin"), "ca", this.username, "control");
				
				connected = true;
			
			}
				
//			if (user.getDescription().length() < 4 || user.getDescription().length() > 15) return false;
//			
//			if (user.getPassword().length() < 5 || user.getPassword().length() > 50) return false;
	
			
			//if user is activate
			user.setActive(true);
			user.setSignature(false);
			
			//Find user
			
			String sentencia = "select usu from User usu where " 
					+ "usu.description = '" + user.getDescription()
					+ "' and usu.password = '" + user.getPassword()  
					+ "' and usu.active = '" +  true + "'";
			
			//List <Base> listUsers = connControl.findObjects(user);
			List <Base> listUsers = connControl.findObjectPerson(sentencia);
			
			//If user exist
			if (connControl.isResultOperation()){
				
				user = (User)listUsers.get(0);
						
				
				if (user.isActive()){
					
					//Locale default user
//					connLog.setUser(user);
//					connControl.setUser(user);
					connEntityDefault(user.getEntityDefault());
//					connEntityDefault("openadmin");
//					
//					if (user.getTerritorio().getDescription().equals("SI")) connEntityTerritorio("territorio_post");
//					
//					if (user.getTercer().getDescription().equals("SI")) connEntityTercer("tercer_post");
					
					this.username = user.getDescription();
					loadPrograms();
					exist = true;
					
					//load Messages languages 
					MessagesTypes.changeLanguage(user.getLanguage());
					return exist;
				}	
			
			} else {
				
				//Data Load configuration
				User _user = new User();
				_user.setDescription("%");
				
				if (connControl.findObjects(_user).size() == 0){
					
					try {
						
						InitDataLoad init = new InitDataLoad();
						init.initializeData();
					
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					
					}
					
				}
				
			}
									
			return exist;
		}
	
	public void logout () {
		
		user = new User();
		connControl.finalize();
		connDefault.finalize();
		
//		if (connTerritorio != null) connTerritorio.finalize();
//		if (connTercer != null) connTercer.finalize();
//		
//		//log.finalizeLog(); 
//		connControl = null;
//		connDefault = null;
//		connTerritorio = null;
//		connTercer = null;
		connLog = null;
		connected = false;
		
	}
	
	private  void loadPrograms() {
		
		
		//Clear view list
		menuItems.clear();
		listEntity.clear();
		
		//List the entity and programs
		List<EntityRole> loadPrograms = new ArrayList<EntityRole>();
		
		
		/*****************************************************************
		 * Find all the Entities that has the user, in the table access
		 *****************************************************************/
		Access access = new Access();
		access.setUser(user);
		//List of user entities

		
		for (Base b : connControl.findObjects(access)) {
			
			access = (Access)b;
			listEntity.add(access.getEntityAdm());
			
		}
		
		/*****************************************************************
		 * Find all programs for each entity and user, in the table access
		 *****************************************************************/
		// List of user entities
		for (EntityAdm en : listEntity) {
			
			access = new Access();;
			access.setEntityAdm(en);
			access.setUser(user);
			
			//if (en.getConn().equals(user.getEntityDefault())) entityDefault = en;
			
			//New list programs
			//Set<Role>  listroles = new TreeSet<Role>();
			
			//Find all programs for each entity and user
			
			Set<Role>  listroles = new TreeSet<Role>();
			
			for (Base b : connControl.findObjects(access)) {
				
				access = (Access)b;
				
				//Load the programs
				listroles.add(access.getRole());
				
				/********************************
				 * Load items of menu for each application
				 *******************************/
				loadMenuItems(access.getRole(), en);
				
			}
			
			loadPrograms.add(new EntityRole(en, listroles));
			
		}
		
		programs = loadPrograms;
		

	}
	
	/**
	 * <desc> Load menu items for each application</desc>
	  * @param pRole, role of application
	  * @param entity, selected entity
	 */	
	private void loadMenuItems(Role pRole, EntityAdm entity) {
		
		/*****************************************************************
		 * Find all items of menu for each entity, application and user, in the table ViewRole
		 *****************************************************************/
		
		ActionViewRole actionViewRole = new ActionViewRole();
		
		actionViewRole.setRole(pRole);
		
		//Load of items menu with the key (id entity and id program)
		menuItems.put("idi" + entity.getId() + "_" + pRole.getProgram().getId(), connControl.findObjects(actionViewRole));
		if(connControl.findObjects(actionViewRole).size()>0) {
		System.out.println("idi" + entity.getId() + "_" + pRole.getProgram().getId() + "Role:  " + pRole.getId() + pRole.getDescription() + "AVR: " + connControl.findObjects(actionViewRole).get(0).getDescription());
		}
		/*****************************************************************
		 * Find all actions for each view and role, in the table ActionViewRole
		 *****************************************************************/
		//List of menu items
		Set<MenuItem> listMenuItem = new TreeSet<MenuItem>();
		
		for (Base b : connControl.findObjects(actionViewRole)) {
			
			actionViewRole = (ActionViewRole)b;
			listMenuItem.add(actionViewRole.getMenuItem());
			
		}
	
		List<Base> lst = new ArrayList<Base>();
		
		lst.addAll(listMenuItem);
		
		for (MenuItem item: listMenuItem){
			
			actionViewRole = new ActionViewRole();
			actionViewRole.setMenuItem(item);
			actionViewRole.setRole(pRole);
			for(ActionViewRole avr: connControl.findObjects(actionViewRole)) {
				
				System.out.println("guardant en el context " + item.getDescription() +" amb " + avr.getAction().getDescription());
				
			}
			//actionsViews.put("ida" + item.getDescription() + "_" + pRole.getId(), connControl.findObjects(actionViewRole));
			actionsViews.put("ida" + item.getDescription(), connControl.findObjects(actionViewRole));
			
			System.out.println("elsactionviews" + actionsViews.toString());
		}
			
	}
	
	public void connEntityDefault(String pconn){
		
		//connection
		if (connDefault != null){
			
			connDefault.finalize();
		}
		
		//connDefault = new DaoJpaHibernate(user, pconn, log);
		
		try {
			connDefault = new DaoJpaHibernate 
					(new ConnectionDao().getEntityManagerDao(pconn), "ca", this.username, "openadmin");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<EntityRole> getPrograms() {
		return programs;
	}
	
	public List<Base> getLoadMenuItems(String pKey){
		
		
		return menuItems.get(pKey);

	} 
	
	/**
	  * <desc> /Loads all actions of the view</desc>
	  * @param pKey, id view and id role
	  * @return List the actions
	  */
	public List<Base> getLoadActionsView(String pKey){

		
		return actionsViews.get(pKey);
		
	}

	
	/**-----------------------------------------------------
	 * ---------------- gets i sets --------------------
	 * -----------------------------------------------------
	 */
	
	public DaoOperationFacade getConnDefault() {
		return connDefault;
	}


	public void setConnDefault(DaoOperationFacade connDefault) {
		this.connDefault = connDefault;
	}


	public DaoOperationFacade getConnControl() {
		return connControl;
	}


	public void setConnControl(DaoOperationFacade connControl) {
		this.connControl = connControl;
	}
	


	public List<EntityAdm> getListEntity() {
		
		List<EntityAdm> lst = new ArrayList<EntityAdm>();
		
		for (EntityAdm b : listEntity) {
			
			lst.add(b);
			
		}
		
		return lst;
	}

//	public LogOperationFacade getLog() {
//		return log;
//	}
//
//	public void setLog(LogOperationFacade log) {
//		this.log = log;
//	}


	public EntityAdm getEntityDefault() {
		return entityDefault;
	}

	public void setEntityDefault(EntityAdm entityDefault) {
		this.entityDefault = entityDefault;
	}
	
	public Map<String, List<Base>> getMenuItems(){
		
		return this.menuItems;
	}

//	public Role getRolDefault() {
//		return rolDefault;
//	}
//
//
//	public void setRolDefault(Role rolDefault) {
//		this.rolDefault = rolDefault;
//	}
//
//	
	//Work view
	public ViewFacade getView(Integer key) {
		
		return lstView.get(key);
	
	}


	public void setView(Integer key, ViewFacade pView) {
		
		lstView.put(key, pView);
		
	}
	
	public void removeView() {
		
		lstView.remove(lstView.size());
		
	}
	
	public void removeViewAll() {
		
		lstView.clear();
		
	}

	
	public Integer sizeView() {
		
		return lstView.size();
		
	} 
	
	
}
