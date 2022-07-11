// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package openadmin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import openDao.model.Base;

/**
 *  Control entity that links the role, user, entityadm and program
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  12-03-2022
 *  @link Base
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/

@Entity
@Table(name = "access", schema = "control", uniqueConstraints = @UniqueConstraint(columnNames =  { "person", "entityadm", "role" }))
@JsonIgnoreProperties(ignoreUnknown=true)
public class Access implements Base, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7296136314627222940L;
	/**
	 * AutoGenerated Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	//@Default(visible=true)
	@Getter @Setter
	private Integer id;
	
	/**
	 *  Attribute that contains entitie's description, unique value
	 */
	@Length(max = 30)
	@NotNull
	@Getter 
	//@Default
	private String description;
	
	/**
	 * Transient attribute that means that the system should make a log on any JPA operation of this class
	 */
	@Transient
	@Getter @Setter
	private boolean debuglog = true;
	
	
	@ManyToOne
	@JoinColumn(name = "role", nullable= false)
	@Getter @Setter
	private Role role;
	
	
	@ManyToOne
	@JoinColumn(name = "person", nullable= false)
	@Getter @Setter
	private User user;

	
	/** attribute that contain the relationship with entity*/
	@ManyToOne
	@JoinColumn(name = "entityadm", nullable = false)
	@Getter @Setter
	private EntityAdm entityAdm;	
	
	@OneToOne
	@JoinColumn(name = "program", nullable = false)
	@Getter @Setter
	private Program program;
	
	
	/** Transient attribute that means that the system should make a historic on any JPA operation of this class*/
	@Transient
	@Getter @Setter
	private boolean historiclog = false;
	
	
	/**
	 * Constructor of the class Access.
	 * without parameters
	 */
	public Access() {
	}

	/**
	 * Constructor of the class Access.
	 * @param pRole, is the role
	 * @param pUser, is the user
	 */
	public Access(Role pRole, User pUser) {
		
		this.role = pRole;
		this.user = pUser;
	}
	
	

	
	public void setDescription(String pDescription) {
		
		this.description = pDescription;
		if(entityAdm != null || user != null || role != null || program != null) {
			this.description = entityAdm.getId() + "_" + user.getId() + "_"  + program.getId();
		}		
	}
}