package openadmin.widgets.jsf.view;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
//import javax.faces.context.FacesContext;
//import org.ajax4jsf.component.html.HtmlAjaxOutputPanel;
//import openadmin.action.ContextAction;
//import openadmin.dao.Base;
//import openadmin.model.control.Action;
//import openadmin.model.control.ActionViewRole;
//import openadmin.util.reflection.CreateInstance;
//import openadmin.util.reflection.ReflectionField;

import org.primefaces.component.outputpanel.OutputPanel;

import jakarta.faces.context.FacesContext;
import openDao.action.ContextAction;
import openDao.model.Base;
import openDao.util.reflection.CreateInstance;
import openDao.util.reflection.ReflectionField;
import openadmin.model.Action;
import openadmin.model.ActionViewRole;

public class InterceptorActionView implements InterceptorViewFacade, Serializable{
	
	private static final long serialVersionUID = 10120901L;

	public void loadView(){
		
		
	}
	
	/**
	 * <desc>class that execute action or load the view</desc>
	 * @author 			Alfred Oliver
	 * @author 			Vicent Borja
	 * @param pKey    	id menuItem and id role 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ClassNotFoundException 
	 */
	public void actionView(List<Base> loadActionView, ContextAction ctx, int numberView) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{ 
		
		FacesContext _context = FacesContext.getCurrentInstance();
		
		OutputPanel outView = (OutputPanel)_context.getViewRoot().findComponent("idform:idview");
		
		outView.getChildren().clear();
		System.out.println("ASI SE QUEDA");		//Action view role
		ActionViewRole actionViewRole = (ActionViewRole) loadActionView.get(0);
		
		Object obj = CreateInstance.instanceObject(actionViewRole.getAction().getNameclass().getDescription());
		
		// If object exist copy object
		if (ctx.sizeView() > 0){
			
			Base _obj = ctx.getView(ctx.sizeView()).getBase();
			 
			if (_obj != null){
			
				ReflectionField refl = new ReflectionField();
				
				obj = refl.copyObject2(_obj, (Base)obj);
				
			}
			
		}
		
		
		//List actions
		List<Action>  lstActions = new ArrayList<Action>();
			
		for (Base actionView: loadActionView){
			System.out.println("--------HEEEEEEEYYY-----" +((ActionViewRole) actionView).getAction().getDescription());
			lstActions.add(((ActionViewRole) actionView).getAction());
			
		}	
		
		//**********************************************************************************
		// ***************   Discriminator view type or action *****************************
		//**********************************************************************************
		
		//Action
		if (actionViewRole.getMenuItem().getViewType().charAt(0) == ':'){
			
			Method m =  obj.getClass().getMethod(actionViewRole.getMenuItem().getViewType().substring(1));
			m.invoke(obj);
		
		}
		
		//Default View
		else if (actionViewRole.getMenuItem().getViewType().equals("default")) {
			
			//Generate panel data
			ViewFacade view = new DefaultView();
			System.out.println("NUMBER VIEW: " + numberView); 
			view.execute(ctx, (Base)obj, lstActions, "ctx.getView(" + numberView + ").base.", actionViewRole.getMenuItem());
			outView.getChildren().add(view.getOutPanel());
			ctx.setView(numberView, view);
			
			
		}		
		//Custom View
//		else {
//			
//			//Generate panel data
//			ViewFacade view = new Custom2View();
//			System.out.println("NUMBER VIEW custom: " + numberView);
//			view.execute(ctx, (Base)obj, lstActions, "ctx.getView(" + numberView + ").base.", actionViewRole.getMenuItem());
//			outView.getChildren().add(view.getOutPanel());
//			ctx.setView(numberView, view);
//			
//		}
		
		//***********************************************************************************
		//***********************************************************************************
		
	}


}
