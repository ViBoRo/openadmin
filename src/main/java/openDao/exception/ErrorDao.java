package openDao.exception;

public class ErrorDao {

    public static final String LOG = null;
	private String message;
	      
    public String DataException(ErrorEnum pError, String pClassError) {
    	
    	if (pError.equals(ErrorEnum.PERSIST)&& pClassError.equals("org.hibernate.exception.ConstraintViolationException")){    		
    		message= "ERROR_PK"; 
    	}
    	
    	else if (pError.equals(ErrorEnum.PERSIST)&& pClassError.equals("org.hibernate.validator.InvalidStateException")){    		
    		message = "ERROR_UNIQUE"; 		
    	}
    	
    	else if (pError.equals(ErrorEnum.UPDATE)&& pClassError.equals("openadmin.dao.exception.DataException")){    		
    		message = "ERROR_UNIQUE";   		
    	} 
    	
    	else if (pError.equals(ErrorEnum.UPDATE)&& pClassError.equals("java.lang.IllegalStateException")){    		
    		message = "ERROR_UNIQUE";     		
    	}   
    	
    	else if (pError.equals(ErrorEnum.FIND_PK)&& pClassError.equals("java.lang.IllegalArgumentException")){    		
    		message = "ERROR_NO_PK";   		
    	}
    	
    	else if (pError.equals(ErrorEnum.COMMIT)&& pClassError.equals("org.hibernate.exception.ConstraintViolationException")){    		
    		message = "ERROR_CONSTRAINT";    		
    	}
    	
    	else if (pError.equals(ErrorEnum.COMMIT)&& pClassError.equals("org.hibernate.validator.InvalidStateException")){    		
    		message = "ERROR_UNIQUE";    		
    	} 
    	
    	else if (pError.equals(ErrorEnum.TRANSACTION)&& pClassError.equals("Cannot invoke \"javax.persistence.EntityManagerFactory.isOpen()\" because \"this.factory\" is nullnull")){    		
    		message = "ERROR_COMMIT"; 		
    	}
    	
//    	else message ="ERROR_GENERIC" + "\n " + pClassError;
    	else message ="ERROR_GENERIC";
    	
    	return message;
    }
   
}
