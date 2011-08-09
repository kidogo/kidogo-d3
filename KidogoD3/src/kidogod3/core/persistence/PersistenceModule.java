package kidogod3.core.persistence;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.AbstractModule;

public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersistenceManagerFactory.class).toProvider(PersistenceManagerFactoryProvider.class);
		bind(PersistenceManager.class).toProvider(PersistenceManagerProvider.class);		
	}

}
