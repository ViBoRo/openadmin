package openadmin.util.validator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import javax.persistence.JoinColumn;
//import javax.persistence.Transient;
//import org.hibernate.validator.Length;
//import org.hibernate.validator.NotNull;
//import openadmin.annotations.Default;
//import openadmin.annotations.Hora;
//import openadmin.dao.Base;
//import openadmin.util.FormatNumero;
//import openadmin.util.messages.MessagesTypes;
//import openadmin.util.messages.WebMessages;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import openDao.annotation.Default;
import openDao.annotation.Hora;
import openDao.model.Base;
import openDao.util.messages.MessagesTypes;
import openDao.util.messages.WebMessages;



/**
 *  Validates declared fields in app model class
 *	@version  1.0
 *  @author Alfred Oliver
*/
public class WebValidator implements Serializable{
	
	private static final long serialVersionUID = 23031001L;
	
	private static boolean result = false;

	public static boolean execute(Base obj){
		
		result = false; 
		
		try {
		
		for (Field f: obj.getClass().getDeclaredFields()){				
			
			//View of fields 
			f.setAccessible(true);
	
			if (f.getName().equals("debuglog") ||
					f.getName().equals("historiclog") ||
					f.getName().equals ("serialVersionUID") ||
					f.isAnnotationPresent(Transient.class)) continue;
			
			if (f.isAnnotationPresent(Default.class)){
				
				if (!f.getAnnotation(Default.class).visible()) continue;
				
			}	
			
			result = Analyzer(f, obj);
		
		}
		
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		}
		
		return result;
	}
	
	/**
	 * Get all the fields initialized
	 * @param String  Identifies the declared type for the field
	 * */
	private static boolean Analyzer (Field f, Base obj) {
		
		boolean _result = false;
		
		try {
		
		//Analyzes not null object 
		if (f.get(obj) == null && f.isAnnotationPresent(NotNull.class)){

			_result = true;
			
			Object[] params = new Object[1];
            params[0] = MessagesTypes.msgLabels(obj.getClass().getSimpleName(), f.getName());
            
            WebMessages.messageErrorParam("error_null", params); 
				
		}
		
		if (f.get(obj) == null && f.isAnnotationPresent(JoinColumn.class) && f.getAnnotation(JoinColumn.class).nullable() == false){
			
			_result = true;
			
			Object[] params = new Object[1];
            params[0] = MessagesTypes.msgLabels(obj.getClass().getSimpleName(), f.getName());
				
            WebMessages.messageErrorParam("error_null", params); 
			
		}
		
		//string null		
		if ((f.isAnnotationPresent(NotNull.class) && f.getType().getSimpleName().equals("String")) 
				&& (((String)f.get(obj)) == null 
				|| ((String)f.get(obj)).length() == 0)) {
						
			_result = true;
			
			Object[] params = new Object[1];
            params[0] = MessagesTypes.msgLabels(obj.getClass().getSimpleName(), f.getName());
				
            WebMessages.messageErrorParam("error_null", params); 
		}
			
		//Analyzes length String 	
		if (f.getType().getSimpleName().equals("String") && ((String)f.get(obj)) != null) {
			
			if (f.isAnnotationPresent(Length.class) && f.getAnnotation(Length.class).min() > 0){
				
				if (((String)f.get(obj)).length() < f.getAnnotation(Length.class).min() ||
					((String)f.get(obj)).length() > f.getAnnotation(Length.class).max()){
					
					_result = true;
					
					Object[] params = new Object[3];
                    params[0] = MessagesTypes.msgLabels(obj.getClass().getSimpleName(), f.getName());
                    params[1] = f.getAnnotation(Length.class).min();
                    params[2] = f.getAnnotation(Length.class).max();
                    
                    WebMessages.messageErrorParam("error_length_min_max", params);    
				}	
			}	
		}
		
		//Comprueba el formato de la hora
		if (f.isAnnotationPresent(Hora.class)){
			
			Pattern horaPattern = Pattern.compile("(1[0-9]|2[0-3]|0?[0-9])((\\:|\\.)([0-5]?[0-9]))?");	

			if (!horaPattern.matcher(((String)f.get(obj))).matches()) {
								
				_result = true;
				
				Object[] params = new Object[1];
	            params[0] = MessagesTypes.msgLabels(obj.getClass().getSimpleName(), f.getName());
	            
	            WebMessages.messageErrorParam("error_hora", params); 			
			
			} else {
				
				Matcher hora = horaPattern.matcher(((String)f.get(obj)));
			
				while (hora.find()){
					
					String horaR = hora.group(1);
					
					String minutos = hora.group(4);
								
					if(hora.group(4) == null){
						
						minutos = "00";
					
					} 					
					f.set(obj, FormatNumero.formatNumero2Digits(horaR) + ":" + FormatNumero.formatNumero2Digits(minutos));
				}
				
			}
		}
						
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		
		}
		
		if (result) _result = true;
		
		return _result ;
		
	}
	
}
