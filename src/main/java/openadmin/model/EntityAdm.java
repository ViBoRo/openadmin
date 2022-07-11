package openadmin.model;

/**
 *  Control entity that stores the datasource information
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  12-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
 *  @see 
*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

//import openadmin.annotations.Default;
import openDao.model.Base;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "entityadm", schema = "control", uniqueConstraints = {@UniqueConstraint(columnNames = "code"), @UniqueConstraint(columnNames="description")})
public class EntityAdm implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contains the identifier*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Default(visible=true)
	@Getter @Setter
	private Integer id;
	
	/** attribute that contains the entityCode*/
	@NotNull
	@Getter @Setter
	private Integer code;		

	/** attribute that contains the description*/
	@NotNull
	@Length(min = 4, max = 25)
	@Getter
	private String description;
	
	@Length(max = 50)
	@Getter @Setter
	private String icon;
	
	@Length(max = 25)
	@Getter @Setter
	private String conn;
	
	@Length(max = 25)
	@Getter @Setter
	private String connhistoric;
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	@Transient 
	@Getter @Setter
	private boolean debuglog = true;
	
	/** Transient attribute that means that the system should make a historic on any JPA operation of this class*/
	@Transient
	@Getter @Setter
	private boolean historiclog = false;
		
	/**
	 * Constructor of the class ActionClass.
	 * without parameters
	 */
	public EntityAdm(){
		
	}

	/**
	 * Constructor of the class Entity.
	 * @param pDescription, is the description, (unique value), of the Entity
	 */
	public EntityAdm(String pDescription){
		this.description = pDescription.toLowerCase();
	}
	


	public void setDescription(String pDescription) {
		
		this.description = pDescription.toLowerCase();
		
	}

	@Override
	public String toString(){
	      
		return  " " + description;
	
	}



}
