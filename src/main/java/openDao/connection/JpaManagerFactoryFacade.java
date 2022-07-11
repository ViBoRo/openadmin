package openDao.connection;

import jakarta.persistence.EntityManager;

/**
 * This class implements the JPA hibernate API, contains the methods for processing the information of the
 * configuration file and makes the connection to the database. The getEntityManager() will return 
 * the EntityManager that is used for making operations in the database.
 * @see <a href = "https://www.arquitecturajava.com/ejemplo-de-jpa/" > arquitecturajava.com � Ejemplo de jpa </a>
 */
public interface JpaManagerFactoryFacade {
	 
     /**
     * Creates and returns the EntityManager that was generated by the EntityManagerFactory
     * @return EntityManager the object generated once the connection was created succesfully.
     * @see getEntityManagerFactory()
     */
    public EntityManager getEntityManagerDao(String pFileName);
   
}
