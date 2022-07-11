package openadmin.util.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;



import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import openDao.connection.ConnectionDao;
import openDao.operation.DaoJpaHibernate;
import openDao.operation.DaoOperationFacade;
import openadmin.model.Access;
import openadmin.model.Action;
import openadmin.model.ActionViewRole;
import openadmin.model.ConfigData;
import openadmin.model.CustomViewAtributte;
import openadmin.model.EntityAdm;
import openadmin.model.MenuItem;
import openadmin.model.Program;
import openadmin.model.Role;
import openadmin.model.User;



/**
 *  Class that implements Jackson library to process control acces data inside yaml
 *	@version  1.0
 *  Created  17-02-2022
 *  @author Vicent Borja
*/
public class InitDataLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -700710496950186136L;
	
	/**
	 * Initialaize control acces configuration of views and user access
	 */
	public void initializeData() throws ClassNotFoundException {

		// get EntityManager
		DaoOperationFacade dao = new DaoJpaHibernate 
				(new ConnectionDao().getEntityManagerDao("openadmin"), "ca", "alfred", "control");

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();

		try {
			File yamlfile = new File("D:/EclipseWorkspaceNou/openControl/src/main/resources/control_data.yaml");
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ConfigData data = mapper.readValue(yamlfile, ConfigData.class);	
			JsonNode datanodes = mapper.readTree(yamlfile);	
			
			//ConfigData data = mapper.readValue(yamlfile, ConfigData.class);
			

			// System.out.println(data.users[0].getDescription() +" "+
			// data.users[0].getLanguage() + " "+ data.users[0].getFullName() );
			// System.out.println(data.users[1].getDescription() +" "+
			// data.users[1].getLanguage() + " "+ data.users[1].getFullName() );
			// System.out.println(data.fieldsviews[0].getDescription());

			// -----------------USERS-----------------
			
			dao.begin();
			
			for (User u : data.users) {
				if(dao.findObjectDescription(u) == null) {
					dao.persistObject(u);
				}
				
			}

	
			
//			

			
//			// -----------------PROGRAMS-----------------
		
			for (Program p : data.programs) {
				if(dao.findObjectDescription(p) == null) {
					dao.persistObject(p);
				}
			}
	
			
			
			
//			// -----------------ROLES-----------------
	
			int roleindex = 0;
			JsonNode rolenode = datanodes.path("roles");
			
			for (Role r : data.roles) {
				if(dao.findObjectDescription(r) == null) {
					Program programaux = new Program();
					programaux.setDescription(rolenode.get(roleindex).get("program").toString().replace("\"",""));	
					r.setProgram((Program) dao.findObjectDescription(programaux));
					roleindex++;
				
					dao.persistObject(r);
				}
			}
		


//			// -----------------ETITYADM-----------------
			
		
			
			for (EntityAdm e : data.entityadmins) {
				if(dao.findObjectDescription(e) == null) {
					dao.persistObject(e);
				}
			}

			// -----------------ACCESS-----------------
		
			
			JsonNode accessnode = datanodes.path("access");
			
			for (int i = 0; i <= accessnode.size() - 1 ; i++) {
				
				Access newAccess = new Access();
						
				User useraux = new User();
				useraux.setDescription(accessnode.get(i).get("user").toString().replace("\"",""));	
				newAccess.setUser((User) dao.findObjectDescription(useraux));
				
				EntityAdm entityadmaux = new EntityAdm();
				entityadmaux.setDescription(accessnode.get(i).get("entityadm").toString().replace("\"",""));	
				newAccess.setEntityAdm((EntityAdm) dao.findObjectDescription(entityadmaux));
		
				Role roleaux = new Role();
				roleaux.setDescription(accessnode.get(i).get("role").toString().replace("\"",""));	
				newAccess.setRole((Role) dao.findObjectDescription(roleaux));
						
				Program programaux = new Program();		
				programaux.setDescription(accessnode.get(i).get("program").toString().replace("\"",""));	
				newAccess.setProgram((Program) dao.findObjectDescription(programaux));
				
				newAccess.setDescription(accessnode.get(i).get("description").toString());
				
				if(dao.findObjectDescription(newAccess) == null) {
					dao.persistObject(newAccess);
				}
			}
	

//			// -----------------ACTION AND NAMECLASS-----------------
	
			for (Action a : data.actions) {
					if(dao.findObjectDescription(a) == null) {
						
						if(dao.findObjectDescription(a.getNameclass()) == null) {
							dao.persistObject(a.getNameclass());
							a.setDescription(a.getNameclass().getDescription() + "_" + a.getDescription());
							dao.persistObject(a);
							continue;
						}
						a.setDescription(a.getNameclass().getDescription() + "_" + a.getDescription());
						a.setNameclass(dao.findObjectDescription(a.getNameclass()));
						dao.persistObject(a);
					}
			}
//
//			// -----------------MENUITEMS-----------------
			
//			JsonNode menuitemsnode = datanodes.path("menuitems");
//			int contadormenus = data.menuitems.length;
//			
//			//descontar repetidos
//			for(MenuItem m : data.menuitems) {
//				
//				if(dao.findObjectDescription(m) == null) {
//						
//						contadormenus --;
//				}
//			
//			}
//			
//			while(contadormenus != 0) {
//				for (MenuItem m : data.menuitems) {
//				
//					if(dao.findObjectDescription(m) == null) {
//						//persist als pares
//						if(m.getParent() == null) {
//							dao.persistObject(m);
//							contadormenus --;
//						}		
//						
//						//si te pare i el pare esta 
//						if(m.getParent() != null && dao.findObjectDescription(m.getParent()) != null) {
//							dao.persistObject(m);
//							contadormenus --;
//						}
//					}
//						
//			}//for
//			}//while
			
			JsonNode menuitemsnode = datanodes.path("menuitems");
			int contadormenus = menuitemsnode.size();
			System.out.println(menuitemsnode.size());
			
			//tener en cuenta los que existen para el contador
			for(int i = 0; i <= menuitemsnode.size() - 1 ; i++) {
				
				MenuItem newMenuItem = new MenuItem();
				newMenuItem.setDescription(menuitemsnode.get(i).get("description").toString().replace("\"",""));
				
				if(dao.findObjectDescription(newMenuItem) != null) {
						
						contadormenus --;
				}
			
			}
			
			System.out.println(contadormenus);
			
			
			while(contadormenus != 0) {
				System.out.println("bucle");
				System.out.println(contadormenus);
				
				for (int i = 0; i <= menuitemsnode.size() - 1 ; i++) {
					
					//rellenar campos menuitem
					MenuItem newMenuItem = new MenuItem();
					newMenuItem.setDescription(menuitemsnode.get(i).get("description").toString().replace("\"",""));
					newMenuItem.setTypenode(menuitemsnode.get(i).get("typenode").toString().replace("\"",""));
					newMenuItem.setViewType(menuitemsnode.get(i).get("viewType").toString().replace("\"",""));
					newMenuItem.setViewCode(menuitemsnode.get(i).get("viewCode").toString().replace("\"",""));
					newMenuItem.setIcon(menuitemsnode.get(i).get("icon").toString().replace("\"",""));
					newMenuItem.setItemorder((byte) Integer.parseInt(menuitemsnode.get(i).get("itemorder").toString().replace("\"","")));
					
					
					//comprobar que no este
					if(dao.findObjectDescription(newMenuItem) == null) {
						
						//persist als padres raiz(padres sin padre)
						if(menuitemsnode.get(i).get("parent").toString().equals("null")) {
							dao.persistObject(newMenuItem);
							System.out.println("PersistParent" + newMenuItem.getDescription());
							contadormenus --;
							continue;
						}		
						
						MenuItem parent = new MenuItem();
						parent.setDescription(menuitemsnode.get(i).get("parent").toString().replace("\"",""));
						
						//si te pare i el pare esta 
						if(!menuitemsnode.get(i).get("parent").toString().equals("null") && dao.findObjectDescription(parent) != null) {

							newMenuItem.setParent((MenuItem) dao.findObjectDescription(parent));
							dao.persistObject(newMenuItem);
							System.out.println("PersistChil" + newMenuItem.getDescription());
							contadormenus --;
							
						}
					}
						
			}//for
			}//while
			
			
//			// -----------------CUSTOMVIEATRIBUTTES-----------------

		
			for (CustomViewAtributte c : data.customviewatributtes) {
				if(dao.findObjectDescription(c) == null) {
					dao.persistObject(c.getMenuitem());
					dao.persistObject(c);
				}
			}

			
//			// -----------------ACTIONVIEWROLES-----------------
			
	
			
			JsonNode aviewrolenode = datanodes.path("actionviewroles");
			
			for (int i = 0; i <= aviewrolenode.size() - 1 ; i++) {
					
				ActionViewRole newAviewrole = new ActionViewRole();
				
				Role roleaux = new Role();
				roleaux.setDescription(aviewrolenode.get(i).get("role").toString().replace("\"",""));	
				newAviewrole.setRole((Role) dao.findObjectDescription(roleaux));
					
				MenuItem menuitemaux = new MenuItem();
				menuitemaux.setDescription(aviewrolenode.get(i).get("menuitem").toString().replace("\"",""));	
				newAviewrole.setMenuItem((MenuItem) dao.findObjectDescription(menuitemaux));
	
				Action actionaux = new Action();
				System.out.println("laactionsnsnsn" + aviewrolenode.get(i).get("action").toString().replace("\"",""));
				actionaux.setDescription(aviewrolenode.get(i).get("action").toString().replace("\"",""));	
				newAviewrole.setAction((Action) dao.findObjectDescription(actionaux));
				
				newAviewrole.setDescription(newAviewrole.getRole().getId() +"_" + newAviewrole.getMenuItem().getId() +"_" + newAviewrole.getAction().getId() );
				
				if(dao.findObjectDescription(newAviewrole) == null) {
					dao.persistObject(newAviewrole);
				}
			}
			dao.commit();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/*
	 * public void setRoleProgram() throws IOException {
	 * 
	 * for (Role d: this.data.roles) {
	 * 
	 * Program program = new Program(); //System.out.println(d.getProgramname());
	 * program.setDescription(d.getProgramname()); Program elProgram = (Program)
	 * dao.findObjectDescription(program);
	 * System.out.println(elProgram.getDescription()); d.setProgram(elProgram); }
	 * 
	 * 
	 * }
	 */
}
