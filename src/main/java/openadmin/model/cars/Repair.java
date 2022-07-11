package openadmin.model.cars;

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
import openDao.annotation.Default;
import openDao.model.Base;

@Entity
@Table(name = "repair", schema = "cars", uniqueConstraints = @UniqueConstraint(columnNames = "description"))
public class Repair implements Base, java.io.Serializable{


	private static final long serialVersionUID = 0107202204;

	/** attribute that contain the identifier*/
	@Id
	@Default
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter @Setter
	private Integer id;
	
	
	@Length(max = 30)
	@NotNull
	@Getter @Setter
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "car", nullable= false)
	@Getter @Setter
	private Car car;

	@NotNull
	@Getter @Setter
	private Integer price;
	
	@Getter @Setter
	private Date billDate;
	
	
	public Repair(){
	}
	



	
}
