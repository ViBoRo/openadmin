package openDao.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


/**
/**
 * This class implements the JPA hibernate API, contains the methods for processing the information of the
 * configuration file and makes the connection to the database. The getEntityManager() will return 
 * the EntityManager that is used for making operations in the database.
 * @see <a href = "https://www.arquitecturajava.com/ejemplo-de-jpa/" > arquitecturajava.com – Ejemplo de jpa </a>
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  13-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class ConnectionDao implements JpaManagerFactoryFacade {

	/**Field used by the application to obtain an application-managed entity manager.*/
	private EntityManagerFactory factory = null;
	
	/**Field that contain the interface used to interact with the persistence context.*/
	private EntityManager em;
		
	/**
	  * Procedure that connect to database
	  * {Pre: There isn't an open connection to the database}.
	  * {Post: has connect to database}.
	  *  @return EntityManager the object generated once the connection was created succesfully.
	  * @throws DataException if there isn't a connection to the database.
	  * @param 	pDataBase. Attribute name of node persistence-unit (file persistence.xml)  
	  */
	public EntityManager getEntityManagerDao(String pDataBase){		
						
		try {
			
			factory = Persistence.createEntityManagerFactory(pDataBase);
			em = factory.createEntityManager();
			System.out.println("Conexio creada" + em.getProperties());
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		return em;
	}

}
