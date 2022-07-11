package openadmin.util.configuration;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import openadmin.model.EntityAdm;
import openadmin.model.Role;



public class EntityRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1324388940074221133L;


	private EntityAdm entity;


	private Set<Role> role = new TreeSet<Role>();
	
	public EntityRole(EntityAdm entity, Set<Role> role) {
		
		this.entity = entity;
		this.role = role;
		
	}
	
	//Getters and setters
	public EntityAdm getEntity() {
		return entity;
	}
	
	public void setEntity(EntityAdm entity) {
		this.entity = entity;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	
}
