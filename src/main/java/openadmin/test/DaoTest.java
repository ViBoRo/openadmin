package openadmin.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import openDao.connection.ConnectionDao;
import openDao.operation.DaoJpaHibernate;
import openDao.operation.DaoOperationFacade;
import openadmin.model.Program;
import openadmin.model.cementeri.Zona;
import openadmin.util.data.InitDataLoad;

public class DaoTest {
	@Test
	public void testIt() throws Exception {

	    
		  
		InitDataLoad testload = new InitDataLoad();
		
		testload.initializeData();
		
		
		
}
}