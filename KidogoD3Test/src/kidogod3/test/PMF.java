package kidogod3.test;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
	private static final PersistenceManagerFactory factory =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public static PersistenceManagerFactory get(){
		return factory;
	}
	public static PersistenceManager getPersistenceManager(){
		return factory.getPersistenceManager();
	}
}
