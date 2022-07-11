package openDao.action;

import openDao.model.Base;


/**
 *  CustomAction interface
 *	@version  0.1
 *	Create  18-03-2009
 * Modifier  16-01-2022
 * Author Alfred Oliver
*/
public interface OtherActionFacada {

	/**
	 * Method called when the action EL command is executed
	 * @param pAction Base object with the form information
	 * @param pBase Base object with the form information
	 * @param pCtx context information
	 */	
	public void execute(String pAction, Base pBase, ContextAction ctx);
	
}
