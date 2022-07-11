package openadmin.widgets.jsf.view;



import jakarta.faces.event.ActionEvent;
import openDao.action.ContextAction;
import openDao.model.Base;

/**
 *  Class that contains the definition of the logic for executing actions called from the UI
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  05-04-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public interface ObjectActionFacade {
	

	//Other action
	public void otherAction(ActionEvent event);
	
	public Base getBase();
	public void setBase(Base pBase);
	public ContextAction getCtx();
	public void setCtx(ContextAction ctx);
	public String getMetodo();
}
