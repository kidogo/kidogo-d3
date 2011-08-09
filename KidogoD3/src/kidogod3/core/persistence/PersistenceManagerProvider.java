package kidogod3.core.persistence;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class PersistenceManagerProvider implements Provider<PersistenceManager>{
	
	static final ThreadLocal<PersistenceManager> pm = new ThreadLocal<PersistenceManager>();
	
	Provider<PersistenceManagerFactory> factoryProvider;
	
	@Inject
	public PersistenceManagerProvider(Provider<PersistenceManagerFactory> factoryProvider) {
		this.factoryProvider = factoryProvider;
	}

	@Override
	public PersistenceManager get() {
		if(pm.get()==null || pm.get().isClosed()){
			pm.set(factoryProvider.get().getPersistenceManager());
		}
		return pm.get();
	}

}
