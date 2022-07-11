package openDao.util.messages;

import java.util.ResourceBundle;



/**
 * <responsibility>Class that contains the necessary methods to give the custom error sentence depending
 * on the error class and the prefered language</responsibility>
 * @see Error
*/
public class MessagesTypes {
	
	/** Field that contain the working language*/
	private static String lang = "es";
	
	/** Field that contain the working language log*/
	//private static TypeLanguages langLog = TypeLanguages.es;
	
	
	public static String msgError(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("errorsDao_" + lang);
		System.out.println("HolaHola" + pValue);
		return language.getString(pValue);
	
	}
	
	public static String msgOperation(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("operationDao_" + lang);
		return language.getString(pValue);
	
	}
	
	public static String msgOperationWeb(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("messagesOperation_" + lang);
		return language.getString(pValue);
	
	}
	
	//message dao
	public static String msgDao(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("messagesDao_" + lang);
		return language.getString(pValue);
		
	}
	
	public static String msgMain(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("messagesMain_" + lang);
		return language.getString(pValue);
		
	}
	
	public static String msgLog(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("logDao_"  + lang);
		return language.getString(pValue);
		
	}
	
	//Message log
	/*public static String msgListados(String pValue){
		
		ResourceBundle language = ResourceBundle.getBundle("listados_" + langListados);
		return language.getString(pValue);
		
	}*/
	
	public static void changeLanguage(String pLanguage) {

		lang = pLanguage;
	
	}
	
	public static String msgLabels(String pDomanin, String pValue){
		
		String result = null;
		ResourceBundle language = null;
		
		try {
			
			language = ResourceBundle.getBundle("labels_" + lang);
			
			result = language.getString(pDomanin + "_" +pValue);

			
		}catch (Exception ex) {
			
			try {
			
				System.out.println("Etiqueta " + pValue);
			
				result = language.getString(pValue);
			
			} catch (Exception exc) {
			
				exc.printStackTrace();
			
			}
		}
		
		return result;
	
	}
	
	/*public static void changeMessageLog(TypeLanguages pLanguage) {

		langLog = pLanguage;
	
	}*/
	
	/*public static void changeMessageListados(TypeLanguages pLanguage) {

		langListados = pLanguage;
	
	}
	*/
}
