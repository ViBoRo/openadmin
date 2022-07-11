package openDao.model.log;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import openDao.model.Base;




/**
 * Class that defines the entity for persisting the log
 *	@version  0.1
 *	Create  18-03-2009
 * Modifier  16-01-2022
 * Author Alfred Oliver
*/
@NoArgsConstructor
@Entity
@Table(name = "logdao", schema = "log", uniqueConstraints = @UniqueConstraint(columnNames = "description"))
public class Log implements Base, java.io.Serializable{

	private static final long serialVersionUID = 16012201L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Integer id;

	@Column(length = 70) @Getter @Setter
	private String description;
	
	@Column(length = 30) @Getter @Setter
	private String program;
	
	@Column(length = 30) @Getter @Setter
	private String person;
	
	@Column(length = 30) @Getter @Setter
	private String action;
	
	@Getter @Setter
	private Timestamp  actionDate;
	
	@Getter @Setter
	private Long idObject;
	
	@Column(length = 100) @Getter @Setter
	private String className;
	
	@Column(length = 500) @Getter @Setter
	private String detail;
	

}
