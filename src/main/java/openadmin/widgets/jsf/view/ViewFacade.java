package openadmin.widgets.jsf.view;

import java.util.List;

import org.primefaces.component.panel.Panel;

import openDao.action.ContextAction;
import openDao.model.Base;
import openadmin.model.Action;
import openadmin.model.MenuItem;

//import openadmin.action.ContextAction;
//import openadmin.action.ObjectActionFacade;
//import openadmin.dao.Base;
//import openadmin.model.control.Action;
//import openadmin.model.control.MenuItem;
//
//import org.richfaces.component.html.HtmlPanel;


/**
 *  Class that defines view distribution in relation to app model attributes
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  05-04-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public interface ViewFacade extends ObjectActionFacade{

	/**
	 * Get all the fields initialized
	 * @param base  object type Base
	 * @param pLstActions actions list
	 * @param pObjectDestination object destination
	 * */
	public void execute(ContextAction ctx, Base base, List<Action>  pLstActions, String pObjectDestination, MenuItem pMenuItem);
	
	public Panel getOutPanel();
}
