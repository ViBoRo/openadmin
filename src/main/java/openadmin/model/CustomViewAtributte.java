package openadmin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import openDao.annotation.NoSql;
import openDao.model.Base;


/**
 *  Control entity that defines the customview parameters for the custom interface
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  13-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
@Entity
@Table(name = "atributo", schema = "control", uniqueConstraints = @UniqueConstraint(columnNames = "description") )
public class CustomViewAtributte implements Base, java.io.Serializable {

	private static final long serialVersionUID = 04051101L;

	/** attribute that contains the identifier*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter @Setter
	private Integer id;
	
	/**
	 *  Attribute that contains entitie's description, unique value
	 */
	@Length(max = 79)
	@NotNull
	@Getter @Setter
	private String description;
	
	/** Nombre del campo*/
	@Length(max = 30)
	@NotNull
	@Getter @Setter
	private String nomAtributo;		
	
	/** Atributte that contains the view level*/
	@Length(max = 5)
	@NotNull
	@Getter @Setter
	private String nivel;
	
	/** Attribute for seting editable true or false*/
	@NotNull
	@NoSql
	@Getter @Setter
	private boolean readOnly = false;
	
	/** attribute that contains la longitud del atributo*/	
	@NoSql
	@Getter @Setter
	private Integer longitud = 0;
	
	/** attribute that contains el tamaño de la letra*/
	@Length(max = 2)
	@Getter @Setter
	private String tamano;
	
	/** attribute that contains la longitud del atributo*/
	@Length(max = 3)
	@Getter @Setter
	private String color;
	
	/** attribute that contains the relationship with MenuItem*/
	@ManyToOne
	@JoinColumn(name = "menuitem", nullable= false)
	@Getter @Setter
	//@NoEditObject(custom = true)
	private MenuItem menuitem;
	
	/** attribute that contains la pantalla relacionada*/
	@Length(max = 25)	
	@Getter @Setter
	private String pantalla;
	
	/** attribute that contains la acción a realizar*/
	@Length(max = 50)
	@Getter @Setter
	private String accio;
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	@Transient
	private boolean debuglog = true;
	
	/** Transient attribute that means that the system should make a historic on any JPA operation of this class*/
	@Transient
	private boolean historiclog = false;
	
	/**
	 * Constructor of the class Action.
	 * without parameters
	 */
	public CustomViewAtributte() {
	
	}
	
	/** Getters and setters*/
	


}
