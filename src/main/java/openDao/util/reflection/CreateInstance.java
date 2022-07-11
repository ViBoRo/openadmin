package openDao.util.reflection;

import java.lang.reflect.InvocationTargetException;

public class CreateInstance {

	
	public static Object instanceObject (String pObj){
		
		Object obj = null;
		
		//Instance object
		try {
			
			//System.out.println("Instance class: " + pObj);
			Class<?> cls = Class.forName(pObj);
			
			obj = cls.getDeclaredConstructor().newInstance();
			
			//System.out.println("Objete instanciat: " + pObj);
				
		} catch (ClassNotFoundException e) {
				
			e.printStackTrace();
			
		} catch (InstantiationException e) {
				
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
				
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
		
	}
}
