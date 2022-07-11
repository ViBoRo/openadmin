package openDao.operation;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import lombok.Getter;
import lombok.Setter;
import openDao.exception.ErrorEnum;
import openDao.exception.ErrorDao;
import openDao.model.Base;
import openDao.model.log.Log;
import openDao.util.messages.MessagesTypes;



/**
 * <desc>DaoJpaHibernate implement the interface DaoOperationFacade</desc>
 * <responsibility>Implement the operations of database for framework JPA - Hibernate</responsibility>
 * <coperation>All classes that need to work with database</coperation>
 * @version  0.9
 * Create  18-03-2009
 * Modified 30-12-2021
 * Author Alfred Oliver
*/
public class DaoJpaHibernate implements DaoOperationFacade, Serializable{
	
	private static final long serialVersionUID = 30122101L;
		
	/**Field that contain one list of Base objects */
	private List <Base> listObjects = null;

	/**Field that contain the interface used to interact with the persistence context.*/
	private EntityManager em;
	
	/**Field that contain the interface used to interact with the persistence context.*/
	private DaoJpaHibernate daoLog;
	
	/** Field that contain the result to operation*/
	private boolean resultOperation;
	
	/** Contains the last error message that was generated*/
	@Getter @Setter
	private String message;
	
	private MessagesTypes messageType;
	
	/** Contains errors of exceptions*/
	private ErrorDao err = new ErrorDao();
	
	/** Field that contain the registered User*/
	private String user;
	
	private String program;
	
	private boolean isAudit;
	
	/**
	  * Constructor of class DaoJpaHibernate.
	  * @param PUser. Registered User.  	 
	  * @param 	pEntity. Object connection
	  * @param pLog. To record the operations performed. 
	  * @param pLang. language messages
	  */
	public DaoJpaHibernate(EntityManager pEntity, String pLang, DaoJpaHibernate pDaoLog){
		
			this.em = pEntity;
			messageType = new MessagesTypes();
			messageType.changeLanguage(pLang);
			
			this.daoLog = pDaoLog;
			isAudit = true;
		
			persistLog(null, daoLog.messageType.msgLog("CONNECTION"), null);
		
	}
	
	public DaoJpaHibernate(EntityManager pEntityLog, String pLangLong, String pUser, String pProgram) throws ClassNotFoundException{
		
		this.user = pUser;
		this.program = pProgram;
		this.em = pEntityLog;		
		messageType = new MessagesTypes();
		messageType.changeLanguage(pLangLong);
		isAudit = false;
		
	}
	
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist is not exist. 
	 *  @param obj. Object to persist.
	 */
	public <T extends Base> void persistObject(T obj){
		
		resultOperation = false;		
		
		try{
			if (em.isOpen()){				
				em.persist(obj);	
				setMessage(messageType.msgOperation("PERSIST_OK"));
				resultOperation = true;					
				
				if (isAudit) persistLog(obj, daoLog.messageType.msgLog("PERSIST"), obj.toString());
				//System.out.println(getMessage());
				
			}
		
		} catch(Exception ex) {
			
			resultOperation = false;
			
			ex.printStackTrace();
			rollback();
			
			setMessage(messageType.msgError("ERROR_PERSIST") + ": " + obj.getDescription() 
						+  messageType.msgError(err.DataException(ErrorEnum.PERSIST, 
						ex.getLocalizedMessage().substring(0, ex.getLocalizedMessage().indexOf(":")))));
		}		
	}
	
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update if exist. 
	 */
	public <T extends Base> void updateObject(T obj){
		
		resultOperation = false;
					
		try {
			if (em.isOpen() && obj != null){										
								
				em.merge(obj);
				resultOperation = true;
				setMessage(messageType.msgOperation("UPDATE_OK"));
				
				if (isAudit) persistLog(obj, daoLog.messageType.msgLog("UPDATE"), obj.toString());

			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
			resultOperation = false;
			
			rollback();
			
			setMessage(messageType.msgError("ERROR_UPDATE") + ": " + obj.getDescription() 
					+  messageType.msgError(err.DataException(ErrorEnum.UPDATE, 
							ex.getLocalizedMessage().substring(0, ex.getLocalizedMessage().indexOf(":")))));
			
		}
	}
	
	/**
	 * Remove object. 
	 * {Pre: The object exist}.
	 * {Post: The object is remove. 
	 */
	public <T extends Base> void removeObject(T obj){
		
		resultOperation = false;
		
		try {
			obj = findObjectPK(obj);
			if (em.isOpen() && (obj) != null){
				
				if (isAudit) persistLog(obj, daoLog.messageType.msgLog("REMOVE"), obj.toString());
					
				em.remove(obj);				
				resultOperation = true;	
				setMessage(messageType.msgOperation("REMOVE_OK"));
			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
			resultOperation = false;
			
			rollback();

			setMessage(messageType.msgError("ERROR_REMOVE") + ": " + obj.getDescription() 
					+  messageType.msgError(err.DataException(ErrorEnum.REMOVE, 
							ex.getLocalizedMessage().substring(0, ex.getLocalizedMessage().indexOf(":")))));
			
		}		
	
	}

	/**
	 * Find object of type Base by primary key. 
	 * {Pre: Primary key of object}.
	 * {Post: return object if exist}. 
	 */
	public <T extends Base> T findObjectPK (T obj){
			
		resultOperation = false;
	
		T new_obj = null;
			
		try {
			if (em.isOpen()){
				
				new_obj = (T)em.find(obj.getClass(), obj.getId());														
				resultOperation = true;
				setMessage(messageType.msgOperation("FIND_OK"));
				
				if (isAudit) persistLog(new_obj, daoLog.messageType.msgLog("FIND_OBJECT_id"), new_obj.toString());


			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
			resultOperation = false;
			
			setMessage(messageType.msgError("ERROR_FIND_PK") + ": " + obj.getDescription() 
				+  messageType.msgError(err.DataException(ErrorEnum.FIND_PK, 
					ex.getLocalizedMessage().substring(0, ex.getLocalizedMessage().indexOf(":")))));
		}		
			return new_obj;
	}
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	public <T extends Base> T findObjectDescription(T obj) {
		
		resultOperation = false;
		
		List <Base> lstObjects = null;
		
		T new_base = null;
		
		try {
								
			lstObjects = em.createQuery("select o from " + obj.getClass().getSimpleName() + " o where o.description like :pDescription")
						.setParameter("pDescription", obj.getDescription())
						.getResultList();
			
			if (lstObjects.size() == 1){
			
				new_base = (T) lstObjects.get(0);
				resultOperation = true;
				setMessage(messageType.msgOperation("FIND_OK"));
				
				if (isAudit) persistLog(new_base, daoLog.messageType.msgLog("FIND_OBJECT_des"), new_base.toString());

			}
		
		}
		catch (NoResultException Nex){
			
			//Nex.printStackTrace();
			
			resultOperation = false;
			
			return null;
		
		}
		
		catch(Exception ex) {
			
			resultOperation = false;
			
			ex.printStackTrace();			
		
			setMessage(messageType.msgError("ERROR_FIND_DESCRIPTION") + ": " + obj.getDescription() 
			+  messageType.msgError(err.DataException(ErrorEnum.FIND_DESCRIPTION, 
					ex.getClass().getName())));
		}
		
		return new_base;
		
	}
	
	public <T extends Base> void deleteObjects(T obj) {
		
		resultOperation = false;
		String className = obj.getClass().getSimpleName();		
		AnalyzerConsult c = new AnalyzerConsult();
		String whereClause = c.makeWhere(obj);										
		
		try{
			if (em.isOpen()){										
				
				if (isAudit) persistLog(obj, daoLog.messageType.msgLog("FIND_OBJECT_des"), "delete from " + className + " " + className +  " " + whereClause);
				
				Integer i = em.createQuery("delete from " + className + " " + className +  " " + whereClause)
							.executeUpdate();	
				setMessage(messageType.msgOperation("REMOVE_OBJECTS_OK"));

			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();	
			
			setMessage(messageType.msgError("ERROR_REMOVE") + ": " + obj.getDescription() 
						+  messageType.msgError(err.DataException(ErrorEnum.REMOVE, 
								ex.getClass().getName())));
		}
												
	}
	
	
	/**
	 * Find objects by some criterion. 
	 * {Pre: Some criterion}.
	 * {Post: return List objects if exist}. 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public <T extends Base> List<T> findObjects(T obj) {
		
		listObjects = new ArrayList<Base>();
		
		resultOperation = false;
		String className = obj.getClass().getSimpleName();		
		AnalyzerConsult c = new AnalyzerConsult();
		String whereClause = c.makeWhere(obj);								
		//listObjects.clear();
		
		try{
			if (em.isOpen()){										
																								
				//System.out.println("select " +  className + " from " + className + " " + className + " " + whereClause + " order by " + className + ".getDescription");				
				
				if (isAudit) persistLog(obj, daoLog.messageType.msgLog("FIND_OBJECT"), "select " +  className + " from " + className + " " + className +  " " + whereClause);
				
				listObjects = em.createQuery("select " +  className + " from " + className + " " + className +  " " + whereClause)
							.getResultList();	
				setMessage(messageType.msgOperation("FIND_OBJECTS_OK"));

				
			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
			setMessage(messageType.msgError("ERROR_FIND_OBJECTS") + ": " + obj.getDescription() 
						+  messageType.msgError(err.DataException(ErrorEnum.FIND_OBJECTS, 
								ex.getClass().getName())));
		}
				
		if (listObjects.size() > 0) resultOperation = true;
		
		return (List<T>) listObjects;
	}
	
	public List<Object[]> findObjectPerson (String pSentencia) {
		
		resultOperation = false;
		
		try{
			if (em.isOpen()){										
																								
				//System.out.println("select " +  className + " from " + className + " " + className + " " + whereClause + " order by " + className + ".getDescription");				
				
				if (isAudit) persistLog(null, daoLog.messageType.msgLog("FIND_OBJECT_per"), pSentencia);
				
				List<Object[]> lstObjects = em.createQuery(pSentencia).getResultList();																
				
				if (lstObjects.size() > 0) resultOperation = true;
				
				setMessage(messageType.msgOperation("FIND_OBJECTS_OK"));
				return lstObjects;
			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
													
			setMessage(messageType.msgError("ERROR_FIND_OBJECTS") 
					+  messageType.msgError(err.DataException(ErrorEnum.FIND_OBJECTS, 
								ex.getClass().getName())));
		}
						
		return null;		
	}
	
	
	public void executeSQL(String pSentencia){
		
		resultOperation = false;
		
		try{
			if (em.isOpen()){										
																											
				int result = em.createQuery(pSentencia).executeUpdate();																
				
				if (isAudit) persistLog(null, daoLog.messageType.msgLog("FIND_OBJECT_sql"), pSentencia);
				
				setMessage(messageType.msgOperation("OPERATION_OK"));
				System.out.println(" RESULT " + result);
			}
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
													
			setMessage(messageType.msgError("ERROR_FIND_OBJECTS") 
					+  messageType.msgError(err.DataException(ErrorEnum.FIND_OBJECTS, 
							ex.getClass().getName())));
		}
		
		
	}
	
	/**
	* Result of the operation. 
	*/
	  public boolean isResultOperation() {
		
		  return resultOperation;
	  
	  }	
	
	/**
	  * Procedure that starts the transaction
	  * {Pre: There is an open connection to the database}.
	  * {Post: has initiated the transaction}.
	  * @throws DataException if there isn't a connection to the database.
	  * @throws DataException if there is an error to start the transaction.
	  */
	  public void begin() {
		  try {
				
				if (em.getTransaction().isActive()) return;
				
				em.getTransaction().begin();
				
		  } catch (Exception ex) {
				
				ex.printStackTrace();
				
				setMessage(messageType.msgError("ERROR_BEGIN") 
						+  messageType.msgError(err.DataException(ErrorEnum.TRANSACTION, 
								ex.getClass().getName())));
			}
		  return;
	  }	
	  
	  /**
		* Procedure that end the transaction accepting updates.
		* {Pre: There is an open connection to the database}.
		* {Post: End the transaction}.
		* @throws DataException if there isn't a connection to the database.
		* @throws DataException if there is an error to end the transaction.
		*/
	  public void commit() {
					
			try{  
					
				if (!em.isOpen() || !em.getTransaction().isActive()){

					resultOperation = false;
					return;
				} 				
					
				em.getTransaction().commit();						  	
				
			}catch (Exception ex) {
				
				ex.printStackTrace();
				
				System.out.println("Error " + ex.getLocalizedMessage() + ex.getCause().getClass());
					
				resultOperation = false;			
				
				setMessage(messageType.msgError("ERROR_COMMIT") 
						+  messageType.msgError(err.DataException(ErrorEnum.TRANSACTION, 
								ex.getClass().getName())));
			}
			  
			return;
	  }
	  

	  /**
		* Procedure that end the transaction not accepting updates.
		* {Pre: There is an open connection to the database}.
		* {Post: End the transaction}.
		* @throws DataException if there isn't a connection to the database.
		* @throws DataException if there is an error to end the transaction.
		*/
	  public void rollback() {

			 try{  
					
				 if ((!em.isOpen()) || (!em.getTransaction().isActive())){
						
						resultOperation = false;
						return;				
				} 
				
				em.getTransaction().rollback();			
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
							
				resultOperation = false;
					
				setMessage(messageType.msgError("ERROR_ROLLBACK") 
						+  messageType.msgError(err.DataException(ErrorEnum.TRANSACTION, 
								ex.getClass().getName())));
			}
			
			return;
	  
	  }
	  
	  /**
		* Procedure that closed the connection to database. 
		* {Pre: there isn't more references to object in memory}.
		* {Post: Closed the connection to database}. 
		*/
	  public void finalize() {								
		
		//em.close();
		//factory.close();				
		
	  }

	  public EntityManager getEntityManager() {
			return em;
	  }

	public void isAudit(boolean pAudit) {
		
		this.isAudit = pAudit;
	}

	private <T extends Base> void persistLog(T obj, String pAction, String pDetail) {
		
		Log log = new Log();
		
		log.setProgram(daoLog.program);
		
		log.setPerson(daoLog.user);
				
		log.setAction(pAction);
		
		Long datetime = System.currentTimeMillis();
	    log.setActionDate(new Timestamp(datetime));
	    	    
	    if (obj != null) log.setIdObject(((Number)obj.getId()).longValue());
	    		
	    if (obj != null) log.setClassName(obj.getClass().getPackageName() + "." + obj.getClass().getSimpleName());
	   
	    log.setDetail(pDetail);
	    
	    log.setDescription(daoLog.user+"_" + log.getAction() + "_" + log.getActionDate());
	   	    
	    daoLog.begin();
	    
	    	daoLog.persistObject(log);
	    
	    daoLog.commit();
	
	}

}
