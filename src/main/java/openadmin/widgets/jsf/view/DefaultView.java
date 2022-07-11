package openadmin.widgets.jsf.view;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.validator.constraints.Length;

//import javax.faces.component.html.HtmlPanelGrid;
//import javax.faces.context.FacesContext;

import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.component.panel.Panel;

import jakarta.faces.component.html.HtmlPanelGrid;
import jakarta.faces.context.FacesContext;
import openDao.action.ContextAction;
import openDao.annotation.Combo;
import openDao.annotation.Default;
import openDao.annotation.Load;
import openDao.annotation.NoEditObject;
import openDao.annotation.NoSearch;
import openDao.annotation.Search;
import openDao.annotation.copyObject;
import openDao.model.Base;
import openDao.util.messages.MessagesTypes;
import openDao.util.reflection.CreateInstance;
import openadmin.model.Action;
import openadmin.model.MenuItem;
import openadmin.widgets.jsf.component.TplCalendar;
import openadmin.widgets.jsf.component.TplComboBox;
import openadmin.widgets.jsf.component.TplSimpleComponent;
import openadmin.widgets.jsf.component.TplToolbar;

//import openadmin.action.ContextAction;
//import openadmin.action.ObjectAction;
//import openadmin.annotations.Combo;
//import openadmin.annotations.Default;
//import openadmin.annotations.Load;
//import openadmin.annotations.NoEditObject;
//import openadmin.annotations.NoSearch;
//import openadmin.annotations.Search;
//import openadmin.annotations.copyObject;
//import openadmin.dao.Base;
//import openadmin.model.control.Action;
//import openadmin.model.control.MenuItem;
//import openadmin.util.messages.MessagesTypes;
//import openadmin.util.reflection.CreateInstance;
//import openadmin.widgets.jsf.component.TplCalendar;
//import openadmin.widgets.jsf.component.TplComboBox;
//import openadmin.widgets.jsf.component.TplFileUpload;
//import openadmin.widgets.jsf.component.TplMessages;
//import openadmin.widgets.jsf.component.TplSimpleComponent;
//import openadmin.widgets.jsf.component.TplToolbar;
//import org.ajax4jsf.component.html.HtmlAjaxOutputPanel;
//import org.hibernate.validator.Length;
//import org.richfaces.component.html.HtmlPanel;


/**
 *  Class that defines the default view distribution in relation to app model attributes
 *	@version  1.1
 *  Created  18-03-2009
 *  LastModified  20-03-2022
 *  @author Alfred Oliver
 *  @author Vicent Borja
*/
public class DefaultView extends ObjectAction implements Serializable, ViewFacade{

	private static final long serialVersionUID = 23031001L;
	
	//Out container
	private Panel outPanel;
	
	//Out data
	private OutputPanel outPanelData;
	
	private HtmlPanelGrid panelView = null;
	
	HtmlPanelGrid panelViewObject = null;
	
	private boolean readOnly;
	
	private String objectAction = "";
	
	/**
	 * Generate view
	 * @param base object type Base to generate view
	 * @param pLstActions actions list to generate tool bar
	 */
	public void execute(ContextAction ctx, Base base, List<Action>  pLstActions, String pObjectAction, MenuItem pMenuItem){
		
		
		super.setCtx(ctx);
		super.setBase(base);
		
		//Create exit component 
		createExitComponent();
		
		//Generate tool bar actions 
		outPanel.getChildren().add(TplToolbar.createToolActions(ctx.sizeView() + 1, pLstActions));
		
		//Messages
		//outPanel.getChildren().add(TplMessages.mesage01());
		
		//Titulo
//		outPanel.getChildren().add(TplSimpleComponent.HtmlLabel02(MessagesTypes.msgLabels(null, base.getClass().getSimpleName())));
//		System.out.println("Ellabel" + TplSimpleComponent.HtmlLabel02(MessagesTypes.msgLabels(null, base.getClass().getSimpleName())));
//		outPanel.getChildren().add(TplSimpleComponent.HtmlLabel02("ElView"));
		
		//Tabular view
		panelView = TplSimpleComponent.panelGrid(1);	
		
		//Create view
		for (Field f: base.getClass().getDeclaredFields()){
			
			readOnly = false;
			
			//View of fields 
			f.setAccessible(true);
			
			//Exclusions
			if (	f.getName().equals("debuglog") ||
					f.getName().equals("historiclog") ||
					f.getName().equals ("serialVersionUID") )
					continue;
			
			if (f.isAnnotationPresent(Default.class)){
				
				if (!f.getAnnotation(Default.class).visible()) continue;
				
				readOnly = f.getAnnotation(Default.class).readOnly();
				
			}
		
			
			//Destination object
			objectAction = "#{" + pObjectAction + f.getName() + "}";
			
			//Label
			panelView.getChildren().add
			(TplSimpleComponent.HtmlLabel01(MessagesTypes.msgLabels(base.getClass().getSimpleName(), f.getName())));
			
			//ComboBox
			if (f.isAnnotationPresent(Combo.class)){
				
				panelView.getChildren().add(TplComboBox.HtmlComboBox01("languages", objectAction));
				continue;
			}
		
//			
			//Field type String
			if (f.getType().getSimpleName().endsWith("String")){
					
				System.out.println(base.getClass().getSimpleName()+"_"+f.getName());
				System.out.println("VALOR OBJECTE STRING: " + objectAction);
				
				
				
				//Component
				if(f.getAnnotation(Length.class).max()>=80){
				
					panelView.getChildren().add(TplSimpleComponent.textArea01(f.getAnnotation(Length.class).max(), objectAction , f.getType()));
				
				}
				
//				else if (f.isAnnotationPresent(Load.class)){
//					
//					panelView.getChildren().add(TplFileUpload.fileUpload());
//					
//				}
				
				else{
				
					panelView.getChildren().add(TplSimpleComponent.HtmlInput01(f.getAnnotation(Length.class).max(), readOnly, objectAction , f.getType()));
				
				}
				continue;
			}	
	
			
			//Field type Integer Or Double
			else if (f.getType().getSimpleName().endsWith("Integer") || f.getType().getSimpleName().endsWith("Double") || f.getType().getSimpleName().endsWith("Long")){
				
				if (f.isAnnotationPresent(Length.class)){
					
					panelView.getChildren().add(TplSimpleComponent.HtmlInput01(f.getAnnotation(Length.class).max(), readOnly, objectAction, f.getType()));

					
				}else {
					
					//Component
					panelView.getChildren().add(TplSimpleComponent.HtmlInput01(9, readOnly, objectAction, f.getType()));
				
					
				}
				
				continue;
			}
		
		
			
			//Field type Date
			else if (f.getType().getSimpleName().endsWith("Date")){
				
				//Add calendar component
				panelView.getChildren().add(TplCalendar.calendar01(objectAction, f.getType()));
				continue;
			}
			
			//Field type Boolean
			else if (f.getType().getSimpleName().endsWith("boolean")){
								
				//Component
				panelView.getChildren().add(TplSimpleComponent.checkbox(objectAction));
				continue;
			}
	
//						
//			//If is object
			else if (f.getType().getName().startsWith("openadmin.model")){
				
				Base object = (Base) CreateInstance.instanceObject(f.getType().getName());
				
				for (Field fi: object.getClass().getDeclaredFields()){
					
					if (fi.getName().equals("description")){
						
						panelViewObject = TplSimpleComponent.panelGrid(3);
						
						//Add component
						panelViewObject.getChildren().add
						(TplSimpleComponent.HtmlInput01(fi.getAnnotation(Length.class).max(), 
														true, 
														"#{" + pObjectAction + f.getName() + ".description}",
														String.class));
						
						if (f.isAnnotationPresent(Search.class)){
							
							panelViewObject.getChildren().add(TplSimpleComponent.commandFr2("...", "#{ctx.getView(ctx.sizeView()).searchSelect}", f.getType().getName(), f.getType().getName() + "_" + f.getName(), "../resources/icon/search_2.png"));
							
						} else if (f.isAnnotationPresent(copyObject.class)){
							
							panelViewObject.getChildren().add(TplSimpleComponent.commandFr2("...", "#{ctx.getView(ctx.sizeView()).searchAll2}", f.getType().getName(), f.getName(), "../resources/icon/search_2.png"));
							
						} else if (f.isAnnotationPresent(NoSearch.class)){
							
							
						} else{
							
							panelViewObject.getChildren().add(TplSimpleComponent.commandFr2("...", "#{ctx.getView(ctx.sizeView()).searchAll}", f.getType().getName(), f.getType().getName() + "_" + f.getName(), "../resources/icon/search_2.png"));
						
						}
						
						if (!f.isAnnotationPresent(NoEditObject.class)){
							
							//Edit object
							panelViewObject.getChildren().add(TplSimpleComponent.commandFr2(MessagesTypes.msgLabels("","edit"),  "#{ctx.getView(ctx.sizeView()).editObject}", "id_" + f.getType().getName() + "_default", "id_" + f.getName(), "../resources/icon/new_2.png"));
							
						} else if (f.isAnnotationPresent(NoEditObject.class) && f.getAnnotation(NoEditObject.class).custom()){
							
							//Edit object
							panelViewObject.getChildren().add(TplSimpleComponent.commandFr2(MessagesTypes.msgLabels("","edit"),  "#{ctx.getView(ctx.sizeView()).editObject}", "id_" + f.getType().getName() + "_custom", "id_" + f.getName(), "../resources/icon/new_2.png"));
							
						} 
						
						panelView.getChildren().add(panelViewObject);
						continue;
					}
				}
			}
		}
//				
////				if (f.isAnnotationPresent(Load.class)){
////					
////					panelView.getChildren().add(TplFileUpload.fileUpload());
////					
////				}
//			}
//			
//		}
		
		outPanelData.getChildren().add(panelView);
		outPanel.getChildren().add(outPanelData);
			
	}
	
	private void createExitComponent(){
		
		outPanel = new Panel();
		outPanel.setStyleClass("outPanelView");
		
		FacesContext context = FacesContext.getCurrentInstance();
		 
		outPanelData = (OutputPanel)context.getViewRoot().findComponent("idform:idoutDataTplView");
		 
		if (outPanelData == null) {
			 
			outPanelData = new OutputPanel();
			outPanelData.setId("idoutDataTplView");
			outPanelData.setStyleClass("outDataTplView");
		 
		 }		
	
	}

	public Panel getOutPanel() {
		return outPanel;
	}
	
}
