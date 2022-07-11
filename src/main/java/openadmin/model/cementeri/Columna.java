package openadmin.model.cementeri;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import openDao.model.Base;
import openadmin.model.User;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedV alue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//import javax.persistence.UniqueConstraint;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import org.hibernate.validator.Length;
//import org.hibernate.validator.NotNull;
//import openadmin.model.person.Person;

//import openadmin.dao.Base;


/**
 *  Cemetery app model that contains the niche block information
 *	@version  1.1
 *  Created  22-04-2022
 *  @author Vicent Borja
*/
@Entity
@Table(name = "columna", schema = "cementeri", uniqueConstraints = @UniqueConstraint(columnNames = "description"))

public class Columna implements Base, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 0106202206;

	/** attribute that contain the identifier*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter @Setter
	private Integer id;
	
	/** attribute that contain the column description, unique value*/
	@Length(max = 30)
	@NotNull
	@Getter @Setter
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "bloc", nullable= false)
	@Getter @Setter
	private Bloc bloc;
	
	public Columna(){
	}
	



	@Override
	public String toString(){
		return description;
	}
}
