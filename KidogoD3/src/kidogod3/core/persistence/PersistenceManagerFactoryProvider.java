package kidogod3.core.persistence;

import javax.inject.Provider;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.Singleton;

@Singleton
public class PersistenceManagerFactoryProvider implements
		Provider<PersistenceManagerFactory> {
	
	PersistenceManagerFactory factory;

	private synchronized void buildFactory() {
		if(factory==null){
			factory = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		}		
	}
	
	@Override
	public PersistenceManagerFactory get() {
		if(factory==null){
			buildFactory();
		}
		return factory;
	}
}
