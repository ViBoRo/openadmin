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
 *  Control entity that contains the user role
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  12-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
@Entity
@Table(name = "role", schema = "control", uniqueConstraints = @UniqueConstraint(columnNames="description"))
@JsonIgnoreProperties(ignoreUnknown=true)
public class Role implements Base, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3603910133932385997L;
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Integer id;
	

	/** attribute that contains the role name (description), unique value*/
	@Length(min = 3, max = 30)
	@NotNull
	@Getter @Setter
	private String description; 
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	@Transient
	@Getter @Setter
	private boolean debuglog = true;
	
	/** Transient attribute that means that the system should make a historic on any JPA operation of this class*/
	@Transient
	@Getter @Setter
	private boolean historiclog = false;
	
	/** attribute that contains the relationship with program*/
	@ManyToOne
	@JoinColumn(name = "program", nullable= false)
	@Getter @Setter
	private Program program;
	
	/**
	 * Constructor of the class Role.
	 * without parameters
	 */
	public Role() {
	
	}
	
	public Object getId() {
		return id;
	}

	

	

	
//	public void setDescription(String pDescription) {
//		
//		if (program == null || pDescription == "") return;
//		
//		if (this.description != null){
//			
//			if (this.description.equals(pDescription)) return; 
//		
//		}
//		
//		this.description = program.getId() + "_" + pDescription;
//		this.description = pDescription;
//	}

	
}
