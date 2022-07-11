 package openDao.operation;

import java.util.List;
import jakarta.persistence.EntityManager;

import openDao.model.Base;
//import openadmin.model.control.User;
import openadmin.model.User;

/**
 * <desc>DaoOperationFacade are used to provide information about the operations of database </desc>
 * <responsibility>Represents the pattern DAO</responsibility>
 * <coperation>All classes that need to work with database</coperation>
 * @version  0.1
 * Create  18-03-2009
 * Author Alfred Oliver
*/
public interface DaoOperationFacade {
	
	/**
	  * Procedure that starts the transaction
	  * {Pre: There is an open connection to the database}.
	  * {Post: has initiated the transaction}.
	  * @throws DataException if there isn't a connection to the database.
	  * @throws DataException if there is an error to start the transaction.
	  */
	public void begin();
	
	/**
	  * Procedure that end the transaction accepting updates.
	  * {Pre: There is an open connection to the database}.
	  * {Post: End the transaction}.
	  */
	public void commit();
	 
	/**
	  * Procedure that end the transaction not accepting updates.
	  * {Pre: There is an open connection to the database}.
	  * {Post: End the transaction}.
	  */
	public void rollback();  
	
	/**
	 * Procedure that closed the connection to database. 
	 * {Pre: there isn't more references to object in memory}.
	 * {Post: Closed the connection to database}. 
	 */
	public void finalize();

	public void isAudit(boolean pAudit);
	
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist. 
	 */
	public <T extends Base> void persistObject(T obj);
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update. 
	 */
	//torna objecte??
	public <T extends Base> void updateObject(T obj);
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is removed. 
	 */
	public <T extends Base> void removeObject(T obj);
	
	public <T extends Base> void deleteObjects(T obj);
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is found. 
	 */
	public <T extends Base> T findObjectPK(T obj);
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	public <T extends Base> T findObjectDescription (T obj);
	
	/**
	 * Find objects by some criterion. 
	 * {Pre: Some criterion, if there aren't some criterion, List all object}.
	 * {Post: return List objects if exist}. 
	 */
	public <T extends Base> List<T> findObjects (T obj);
	
	public <T extends Base> List<T> findObjectPerson (String pSentencia);
	
	public void executeSQL (String pSentencia);
	
	public boolean isResultOperation();
	
	public EntityManager getEntityManager();
	
	public String getMessage();
	
}
