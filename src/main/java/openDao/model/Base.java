package openDao.model;

import java.io.Serializable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

/**
  * <desc>interface that stores the unique information for all classes dao</desc>
  * <responsibility>Represents a unique object</responsibility>
  * <coperation>All classes dao</coperation>
  *	@version  0.1
  *	Create  18-03-2009
  * Modifier  16-01-2022
  * Author Alfred Oliver
*/
@MappedSuperclass
public interface Base extends Comparable<Base>, Serializable{
	
	/**
	 * <desc> Accessor reading which gives us the identifier</desc>
	 * <pre> x is an instance of Base</pre>
	 * <post> the return is the identifier of x </post>
	 * @return Identifier of object
	 */	
	public Object getId();
	
	public void setId(Integer pId);	
	/**
	 * <desc> Accessor reading which gives us the description</desc>
	 * <pre> x is an instance of Base</pre>
	 * <post> the return is the description of x </post>
	 * @return Description of object
	 */	
	public String getDescription();
	
	public void setDescription(String pDescription);
	
	/**
	 * <desc> Accessor reading that means that the system should make a log on any JPA operation of this class</desc>
	 * <pre> x is an instance of Base</pre>
	 * <post> the return is the audit of x </post>
	 * @return Debug log mode of object
	 */	
	@Transient
	public default boolean isAudit(boolean pAudit) {return pAudit;}

	public default int compareTo(Base o) {
		  
		return getDescription().compareTo(o.getDescription());
	
	}
		
}