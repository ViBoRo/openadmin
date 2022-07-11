package openDao.model.log;

import java.util.List;

import openDao.model.Base;



/**
 * <desc>LogOperationFacade are used to provide information about the operations of Log </desc>
 * <responsibility>Represents the pattern Facade, registered all operation with database</responsibility>
 * <coperation>DaoOperationFacade</coperation>
 * @version  0.1
 * Create  18-03-2009
 * Author Alfred Oliver
*/
public interface LogOperationFacade {
	
	/**
	  * Procedure that open one trace for registered operations performed to database 	  
	  */
	public void openTrace();
	
	/**
	  * Procedure that closed the trace 	  
	  */
	public void closedTrace();
	
	/**
	  * Procedure to persist the action perform in database
	  * {Pre: action initialized}.
	  * {Post: persist the action}.
	  */
	public void recordLog(List<String> listLog);
	public void detailLog(Base objectOriginal, Base objectUpdate);
	public void changeProgram(String pProgram);
	public void finalizeLog();
	public void activateLog(Boolean pDebug);
	public void activatehistoric(Boolean pHistoric);
	
}
